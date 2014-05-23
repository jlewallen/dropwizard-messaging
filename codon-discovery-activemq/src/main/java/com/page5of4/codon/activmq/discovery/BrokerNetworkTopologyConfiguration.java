package com.page5of4.codon.activmq.discovery;

import com.page5of4.codon.BusConfiguration;
import com.page5of4.codon.EndpointAddress;
import com.page5of4.codon.impl.BusConfigurationTopologyConfiguration;
import com.page5of4.codon.impl.TopologyConfiguration;
import org.springframework.beans.factory.annotation.Autowired;

public class BrokerNetworkTopologyConfiguration implements TopologyConfiguration {
   private final BusConfigurationTopologyConfiguration configurationTopology;
   private final BusConfiguration configuration;

   @Autowired
   public BrokerNetworkTopologyConfiguration(BusConfiguration configuration) {
      this.configuration = configuration;
      this.configurationTopology = new BusConfigurationTopologyConfiguration(configuration);
   }

   @Override
   public EndpointAddress getLocalAddressOf(Class<?> message) {
      return configurationTopology.getLocalAddressOf(message);
   }

   @Override
   public EndpointAddress getOwner(Class<?> message) {
      throw new RuntimeException();
   }

   @Override
   public EndpointAddress getSubscriptionAddressOf(Class<?> otherMessage, Class<?> message) {
      return new EndpointAddress(configuration.getApplicationName() + ":subscribe." + otherMessage.getName());
   }
}
