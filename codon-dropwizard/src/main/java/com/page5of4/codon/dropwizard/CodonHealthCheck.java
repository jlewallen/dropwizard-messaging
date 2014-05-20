package com.page5of4.codon.dropwizard;

import com.codahale.metrics.health.HealthCheck;

public class CodonHealthCheck extends HealthCheck {
   private final ManagedCodon managedCodon;

   public CodonHealthCheck(ManagedCodon managedCodon) {
      this.managedCodon = managedCodon;
   }

   @Override
   protected Result check() throws Exception {
      return Result.healthy();
   }
}
