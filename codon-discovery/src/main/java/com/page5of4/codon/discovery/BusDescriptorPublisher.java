package com.page5of4.codon.discovery;

import com.page5of4.codon.BusConfiguration;
import com.page5of4.codon.BusEvents;
import com.page5of4.codon.CommunicationConfiguration;
import com.page5of4.codon.impl.TopologyConfiguration;
import com.page5of4.dropwizard.discovery.zookeeper.ServiceRegistry;
import org.apache.curator.framework.CuratorFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.UUID;

public class BusDescriptorPublisher implements BusEvents {
   private static final Logger logger = LoggerFactory.getLogger(BusDescriptorPublisher.class);
   private final BusDescriptor selfDescriptor = new BusDescriptor();
   private final BusConfiguration busConfiguration;
   private final TopologyConfiguration topologyConfiguration;
   private final CuratorFramework curator;

   public BusDescriptorPublisher(BusConfiguration busConfiguration, TopologyConfiguration topologyConfiguration, CuratorFramework curator) {
      this.busConfiguration = busConfiguration;
      this.topologyConfiguration = topologyConfiguration;
      this.curator = curator;

      CommunicationConfiguration localCommunication = busConfiguration.findCommunicationConfiguration(busConfiguration.getApplicationName());
      this.selfDescriptor.setApplicationName(busConfiguration.getApplicationName());
      this.selfDescriptor.setBrokerUrl(localCommunication.getUrl());
      this.selfDescriptor.setInstanceName(UUID.randomUUID().toString());
   }

   @Override
   public void starting() {

   }

   @Override
   public void started() {
      ServiceRegistry.get().publish(selfDescriptor);
      ServiceRegistry.get().watch(BusDescriptor.class, new ServiceRegistry.ServicesWatch<BusDescriptor>() {
         @Override
         public void notify(Collection<BusDescriptor> busDescriptors) {
            logger.info("Bus Topology Changed: {}", busDescriptors);
         }
      });
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
      selfDescriptor.addListener(messageType.getName(), topologyConfiguration.getLocalAddressOf(messageType).toString());
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
