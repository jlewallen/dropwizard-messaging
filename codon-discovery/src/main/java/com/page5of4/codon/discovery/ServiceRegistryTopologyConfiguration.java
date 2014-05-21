package com.page5of4.codon.discovery;

import com.page5of4.codon.BusConfiguration;
import com.page5of4.codon.EndpointAddress;
import com.page5of4.codon.impl.BusConfigurationTopologyConfiguration;
import com.page5of4.codon.impl.TopologyConfiguration;
import org.springframework.beans.factory.annotation.Autowired;

public class ServiceRegistryTopologyConfiguration implements TopologyConfiguration {
   private static final String MESSAGE_TYPE = "messageType";
   private final BusConfigurationTopologyConfiguration configuration;

   @Autowired
   public ServiceRegistryTopologyConfiguration(BusConfiguration configuration) {
      this.configuration = new BusConfigurationTopologyConfiguration(configuration);
   }

   @Override
   public EndpointAddress getLocalAddressOf(Class<?> message) {
      return configuration.getLocalAddressOf(message);
   }

   @Override
   public EndpointAddress getOwner(Class<?> message) {
      throw new RuntimeException();
   }

   @Override
   public EndpointAddress getSubscriptionAddressOf(Class<?> otherMessage, Class<?> message) {
      throw new RuntimeException();
   }
}
