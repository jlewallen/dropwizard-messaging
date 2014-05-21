package com.page5of4.codon.discovery;

import com.page5of4.codon.BusConfiguration;
import com.page5of4.codon.BusEvents;
import com.page5of4.codon.CommunicationConfiguration;
import com.page5of4.codon.impl.TopologyConfiguration;
import com.page5of4.dropwizard.discovery.zookeeper.ServiceRegistry;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class BusDescriptorPublisher implements BusEvents {
   private static final Logger logger = LoggerFactory.getLogger(BusDescriptorPublisher.class);
   private final BusDescriptor descriptor = new BusDescriptor();
   private final BusConfiguration busConfiguration;
   private final TopologyConfiguration topologyConfiguration;
   private final CuratorFramework curator;

   public BusDescriptorPublisher(BusConfiguration busConfiguration, TopologyConfiguration topologyConfiguration, CuratorFramework curator) {
      this.busConfiguration = busConfiguration;
      this.topologyConfiguration = topologyConfiguration;
      this.curator = curator;

      CommunicationConfiguration localCommunication = busConfiguration.findCommunicationConfiguration(busConfiguration.getApplicationName());
      this.descriptor.setApplicationName(busConfiguration.getApplicationName());
      this.descriptor.setBrokerUrl(localCommunication.getUrl());
   }

   @Override
   public void started() {
      ServiceRegistry.get().publish(descriptor);
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
      descriptor.addListener(messageType.getName(), topologyConfiguration.getLocalAddressOf(messageType).toString());
   }

   @Override
   public void unlisten(Class<?> messageType) {
      throw new RuntimeException("Not supported");
   }

   @Override
   public void stopped() {
      ServiceRegistry.get().unpublish(descriptor);
   }

   public static class TopographerWatcher implements CuratorListener {
      @Override
      public void eventReceived(CuratorFramework client, CuratorEvent event) throws Exception {

      }
   }
}
