package com.page5of4.codon.dropwizard;

import com.google.common.collect.Lists;
import com.page5of4.dropwizard.activemq.ActiveMqBroker;
import com.page5of4.dropwizard.activemq.ActiveMqHealthCheck;
import com.page5of4.dropwizard.activemq.BrokerConfiguration;
import io.dropwizard.Configuration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class CodonBundle implements ConfiguredBundle<ConfiguresCodon> {
   private static final Logger logger = LoggerFactory.getLogger(CodonBundle.class);
   private List<Class<?>> configurationClasses = Lists.newArrayList();

   public CodonBundle() {
   }

   public CodonBundle(Class<?>... someConfigurations) {
      for(Class<?> cfg : someConfigurations) {
         this.configurationClasses.add(cfg);
      }
      this.configurationClasses.add(DropwizardConfig.class);
   }

   @Override
   public void initialize(Bootstrap<?> bootstrap) {
   }

   @Override
   public void run(ConfiguresCodon configuration, Environment environment) throws Exception {
      BrokerConfiguration brokerConfiguration = configuration.getCodonConfiguration().getBroker();
      if(brokerConfiguration.getEnabled()) {
         environment.lifecycle().manage(new ActiveMqBroker(brokerConfiguration));
         environment.healthChecks().register("activemq", new ActiveMqHealthCheck());
      }

      CodonConfiguration codonConfiguration = configuration.getCodonConfiguration();
      if(codonConfiguration.getEnabled()) {
         ApplicationContext applicationContext = codonConfiguration.createApplicationContext(Configuration.class.cast(configuration), environment, configurationClasses);
         environment.lifecycle().manage(applicationContext.getBean(ManagedCodon.class));
         environment.jersey().register(applicationContext.getBean(CodonResource.class));
         environment.healthChecks().register("codon", applicationContext.getBean(CodonHealthCheck.class));
      }
   }
}
