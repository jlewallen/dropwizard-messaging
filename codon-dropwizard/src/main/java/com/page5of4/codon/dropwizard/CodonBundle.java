package com.page5of4.codon.dropwizard;

import com.google.common.collect.Lists;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.util.List;

public class CodonBundle implements ConfiguredBundle<ConfiguresCodon> {
   private List<Class<?>> configurations = Lists.newArrayList();

   public CodonBundle() {
   }

   public CodonBundle(Class<?> configuration) {
      this.configurations.add(configuration);
   }

   @Override
   public void initialize(Bootstrap<?> bootstrap) {
   }

   @Override
   public void run(ConfiguresCodon configuration, Environment environment) throws Exception {
      environment.lifecycle().manage(new ManagedCodon(configurations));
   }
}
