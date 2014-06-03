package com.page5of4.codon.activmq.discovery;

import com.google.common.collect.Lists;
import com.page5of4.codon.BusConfiguration;
import com.page5of4.codon.BusEvents;
import com.page5of4.codon.CommunicationConfiguration;
import com.page5of4.codon.Subscriber;
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
   private final ActiveMqServiceDescriptor selfDescriptor;
   private final Subscriber subscriber;
   private final CuratorFramework curator;
   private final BrokerService broker;

   public ActiveMqNetworkManager(BusConfiguration busConfiguration, Subscriber subscriber, CuratorFramework curator, BrokerService broker) {
      this.subscriber = subscriber;
      this.curator = curator;
      this.broker = broker;

      CommunicationConfiguration localCommunication = busConfiguration.findCommunicationConfiguration(busConfiguration.getApplicationName());
      this.selfDescriptor = new ActiveMqServiceDescriptor();
      this.selfDescriptor.setApplicationName(busConfiguration.getApplicationName());
      this.selfDescriptor.setBrokerUrl(localCommunication.getUrl());
      this.selfDescriptor.setInstanceName(UUID.randomUUID().toString());
   }

   public class Watcher implements ServiceRegistry.ServicesWatch<ActiveMqServiceDescriptor> {
      @Override
      public void notify(Collection<ActiveMqServiceDescriptor> descriptors) {
         logger.info("Topology Changed: {}", descriptors);
         List<NetworkConnector> before = Lists.newArrayList(broker.getNetworkConnectors());
         for(ActiveMqServiceDescriptor other : descriptors) {
            String url = "static:(" + other.getBrokerUrl() + ")";
            if(!other.getBrokerUrl().equals(selfDescriptor.getBrokerUrl())) {
               createBridge(before, other, url);
            }
            else {
               logger.debug("Ignoring self: {}", other.getBrokerUrl());
            }
         }

         for(NetworkConnector shouldRemove : before) {
            logger.info("Removing network connector: {}", shouldRemove.getName());
            try {
               shouldRemove.stop();
            }
            catch(Exception e) {
               logger.error("Error stopping NetworkConnector", e);
            }
            broker.removeNetworkConnector(shouldRemove);
         }

         subscriber.subscribeAll();
      }

      private void createBridge(List<NetworkConnector> before, ActiveMqServiceDescriptor otherBroker, String url) {
         try {
            NetworkConnector existing = broker.getNetworkConnectorByName(otherBroker.getInstanceName());
            if(existing == null) {
               logger.info("Adding network connector: {} to {} ({})", url, otherBroker.getApplicationName(), otherBroker.getInstanceName());
               NetworkConnector networkConnector = broker.addNetworkConnector(url);
               networkConnector.setName(otherBroker.getInstanceName());
               networkConnector.setBrokerName(broker.getBrokerName());
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
   }

   @Override
   public void starting() {
      ServiceRegistry.get().publish(selfDescriptor);
      ServiceRegistry.get().watch(ActiveMqServiceDescriptor.class, new Watcher());
   }

   @Override
   public void started() {

   }

   @Override
   public void subscribe(Class<?> messageType) {
   }

   @Override
   public void unsubscribe(Class<?> messageType) {

   }

   @Override
   public void listen(Class<?> messageType) {
   }

   @Override
   public void unlisten(Class<?> messageType) {

   }

   @Override
   public void stopped() {
      ServiceRegistry.get().unpublish(selfDescriptor);
   }
}
