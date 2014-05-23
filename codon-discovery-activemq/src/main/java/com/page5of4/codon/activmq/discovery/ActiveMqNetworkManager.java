package com.page5of4.codon.activmq.discovery;

import com.google.common.collect.Lists;
import com.page5of4.codon.BusConfiguration;
import com.page5of4.codon.BusEvents;
import com.page5of4.codon.CommunicationConfiguration;
import com.page5of4.codon.impl.TopologyConfiguration;
import com.page5of4.dropwizard.discovery.zookeeper.ServiceRegistry;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.network.NetworkConnector;
import org.apache.curator.framework.CuratorFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class ActiveMqNetworkManager implements BusEvents {
   private static final Logger logger = LoggerFactory.getLogger(ActiveMqNetworkManager.class);
   private final ActiveMqServiceDescriptor selfDescriptor = new ActiveMqServiceDescriptor();
   private final CuratorFramework curator;
   private final BrokerService broker;

   public ActiveMqNetworkManager(BusConfiguration busConfiguration, TopologyConfiguration topologyConfiguration, CuratorFramework curator, BrokerService broker) {
      this.curator = curator;
      this.broker = broker;

      CommunicationConfiguration localCommunication = busConfiguration.findCommunicationConfiguration(busConfiguration.getApplicationName());
      this.selfDescriptor.setApplicationName(busConfiguration.getApplicationName());
      this.selfDescriptor.setBrokerUrl(localCommunication.getUrl());
      this.selfDescriptor.setInstanceName(UUID.randomUUID().toString());
   }

   @Override
   public void starting() {
      ServiceRegistry.get().publish(selfDescriptor);
      ServiceRegistry.get().watch(ActiveMqServiceDescriptor.class, new ServiceRegistry.ServicesWatch<ActiveMqServiceDescriptor>() {
         @Override
         public void notify(Collection<ActiveMqServiceDescriptor> descriptors) {
            logger.info("Topology Changed: {}", descriptors);
            List<NetworkConnector> before = Lists.newArrayList(broker.getNetworkConnectors());
            for(ActiveMqServiceDescriptor other : descriptors) {
               String url = "static:(" + other.getBrokerUrl() + ")";
               if(!other.getBrokerUrl().equals(selfDescriptor.getBrokerUrl())) {
                  try {
                     NetworkConnector existing = broker.getNetworkConnectorByName(other.getInstanceName());
                     if(existing == null) {
                        logger.info("Adding network connector: {} to {} ({})", url, other.getApplicationName(), other.getInstanceName());
                        NetworkConnector networkConnector = broker.addNetworkConnector(url);
                        networkConnector.setName(other.getInstanceName());
                        networkConnector.start();
                     }
                     else {
                        before.remove(existing);
                     }
                  }
                  catch(Exception e) {
                     throw new RuntimeException(e);
                  }
               }
               else {
                  logger.debug("Ignoring self: {}", other.getBrokerUrl());
               }
            }
            for(NetworkConnector shouldRemove : before) {
               logger.info("Removing network connector: {}", shouldRemove.getBrokerURL());
               try {
                  shouldRemove.stop();
               }
               catch(Exception e) {
                  logger.error("Error stopping NetworkConnector", e);
               }
               broker.removeNetworkConnector(shouldRemove);
            }
         }
      });
   }

   @Override
   public void started() {

   }

   @Override
   public void subscribe(Class<?> messageType) {
   }

   @Override
   public void unsubscribe(Class<?> messageType) {
      throw new RuntimeException("Not supported");
   }

   @Override
   public void listen(Class<?> messageType) {
   }

   @Override
   public void unlisten(Class<?> messageType) {
      throw new RuntimeException("Not supported");
   }

   @Override
   public void stopped() {
      ServiceRegistry.get().unpublish(selfDescriptor);
   }
}
