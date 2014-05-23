package com.page5of4.codon.dropwizard;

import com.page5of4.codon.BusConfiguration;
import com.page5of4.codon.subscriptions.SubscriptionStorage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DropwizardConfig {
   @Bean
   public CodonHealthCheck healthCheck(BusConfiguration configuration, SubscriptionStorage subscriptionStorage)  {
      return new CodonHealthCheck(configuration, subscriptionStorage);
   }

   @Bean
   public CodonResource resource(BusConfiguration configuration, SubscriptionStorage subscriptionStorage)  {
      return new CodonResource(configuration, subscriptionStorage);
   }
}
