package com.page5of4.codon.discovery;

import com.page5of4.codon.BusConfiguration;
import com.page5of4.codon.BusEvents;
import com.page5of4.codon.impl.TopologyConfiguration;
import com.page5of4.dropwizard.discovery.zookeeper.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BusDescriptorPublisher implements BusEvents {
   private static final Logger logger = LoggerFactory.getLogger(BusDescriptorPublisher.class);
   private final BusDescriptor descriptor = new BusDescriptor();
   private final BusConfiguration busConfiguration;
   private final TopologyConfiguration topologyConfiguration;

   public BusDescriptorPublisher(BusConfiguration busConfiguration, TopologyConfiguration topologyConfiguration) {
      this.busConfiguration = busConfiguration;
      this.topologyConfiguration = topologyConfiguration;
   }

   @Override
   public void started() {
      logger.info("Publishing BusDescriptor...");
      ServiceRegistry.get().publish(descriptor);
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
}
