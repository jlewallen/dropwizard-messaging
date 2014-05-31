package com.page5of4.codon.dropwizard;

import com.google.common.collect.Lists;
import io.dropwizard.Configuration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CodonBundle implements ConfiguredBundle<ConfiguresCodon> {
   private static final Logger logger = LoggerFactory.getLogger(CodonBundle.class);
   private List<Class<?>> configurations = Lists.newArrayList();

   public CodonBundle() {
   }

   public CodonBundle(Class<?>... someConfigurations) {
      for(Class<?> cfg : someConfigurations) {
         this.configurations.add(cfg);
      }
      this.configurations.add(DropwizardConfig.class);
   }

   @Override
   public void initialize(Bootstrap<?> bootstrap) {
   }

   @Override
   public void run(ConfiguresCodon configuration, Environment environment) throws Exception {
      CodonConfiguration codonConfiguration = configuration.getCodonConfiguration();
      if(codonConfiguration.getEnabled()) {
         ManagedCodon managedCodon = new ManagedCodon(Configuration.class.cast(configuration), configurations);
         codonConfiguration.setBus(managedCodon.getBus());
         environment.lifecycle().manage(managedCodon);
         environment.healthChecks().register("codon", managedCodon.getHealthCheck());
         environment.jersey().register(managedCodon.getResource());
      }
   }
}
