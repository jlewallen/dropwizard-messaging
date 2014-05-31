package com.page5of4.dropwizard.activemq;

import io.dropwizard.ConfiguredBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class LocalActiveMqBundle implements ConfiguredBundle<ConfiguresMessageQueuing> {
   @Override
   public void initialize(Bootstrap<?> bootstrap) {

   }

   @Override
   public void run(ConfiguresMessageQueuing configuration, Environment environment) throws Exception {
      BrokerConfiguration brokerConfiguration = configuration.getBrokerConfiguration();
      if(brokerConfiguration.getEnabled()) {
         environment.lifecycle().manage(new ActiveMqBroker(brokerConfiguration));
         environment.healthChecks().register("activemq", new ActiveMqHealthCheck());
      }
   }
}
