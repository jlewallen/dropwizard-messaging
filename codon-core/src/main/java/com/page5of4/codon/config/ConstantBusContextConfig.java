package com.page5of4.codon.config;

import com.page5of4.codon.BusConfiguration;
import com.page5of4.codon.impl.BusContext;
import com.page5of4.codon.impl.BusContextProvider;
import com.page5of4.codon.impl.ConstantBusContextProvider;
import com.page5of4.codon.impl.TopologyConfiguration;
import com.page5of4.codon.subscriptions.SubscriptionStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value = { PublisherConfig.class })
public class ConstantBusContextConfig {
   @Autowired
   private BusConfiguration busConfiguration;
   @Autowired
   private SubscriptionStorage subscriptionStorage;

   @Bean
   public BusContextProvider busContextProvider() {
      return new ConstantBusContextProvider(busContext());
   }

   @Bean
   public TopologyConfiguration topologyConfiguration() {
      return new TopologyConfiguration(busConfiguration);
   }

   private BusContext busContext() {
      return new BusContext(topologyConfiguration(), subscriptionStorage);
   }
}
