package com.page5of4.dropwizard.activemq;

import com.codahale.metrics.health.HealthCheck;

public class ActiveMqHealthCheck extends HealthCheck {
   @Override
   protected Result check() throws Exception {
      return Result.healthy();
   }
}
