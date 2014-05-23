package com.page5of4.codon.dropwizard;

import com.codahale.metrics.health.HealthCheck;
import com.page5of4.codon.BusConfiguration;
import com.page5of4.codon.subscriptions.SubscriptionStorage;

public class CodonHealthCheck extends HealthCheck {
   private BusConfiguration configuration;
   private SubscriptionStorage subscriptionStorage;

   public CodonHealthCheck(BusConfiguration configuration, SubscriptionStorage subscriptionStorage) {
      this.configuration = configuration;
      this.subscriptionStorage = subscriptionStorage;
   }

   @Override
   protected Result check() throws Exception {
      return Result.healthy();
   }
}
