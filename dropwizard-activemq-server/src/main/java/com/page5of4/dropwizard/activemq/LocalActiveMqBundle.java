package com.page5of4.dropwizard.activemq;

import io.dropwizard.ConfiguredBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class LocalActiveMqBundle implements ConfiguredBundle<ConfiguresMessageQueuing> {
   @Override
   public void run(ConfiguresMessageQueuing configuration, Environment environment) throws Exception {
      environment.lifecycle().manage(new ActiveMqBroker(configuration.getBrokerConfiguration()));
      environment.healthChecks().register("activemq", new ActiveMqHealthCheck());
   }

   @Override
   public void initialize(Bootstrap<?> bootstrap) {

   }
}
