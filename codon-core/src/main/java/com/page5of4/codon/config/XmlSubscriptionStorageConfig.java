package com.page5of4.codon.config;

import com.page5of4.codon.BusConfiguration;
import com.page5of4.codon.subscriptions.SubscriptionStorage;
import com.page5of4.codon.subscriptions.impl.XmlSubscriptionStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class XmlSubscriptionStorageConfig {
   @Autowired
   private BusConfiguration busConfiguration;

   @Bean
   public SubscriptionStorage subscriptionStorage() {
      return new XmlSubscriptionStorage(busConfiguration);
   }
}
