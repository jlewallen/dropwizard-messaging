package com.page5of4.codon.dropwizard;

import com.page5of4.codon.config.StandaloneConfig;
import io.dropwizard.Configuration;
import io.dropwizard.lifecycle.Managed;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Collection;

public class ManagedCodon implements Managed {
   private final AnnotationConfigApplicationContext applicationContext;
   private final Configuration dropwizardConfiguration;
   private final Collection<Class<?>> configurationClasses;

   public ManagedCodon(Configuration dropwizardConfiguration, Collection<Class<?>> configurationClasses) {
      this.dropwizardConfiguration = dropwizardConfiguration;
      this.configurationClasses = configurationClasses;
      this.applicationContext = new AnnotationConfigApplicationContext();
      for(Class<?> configurationClass : configurationClasses) {
         applicationContext.register(configurationClass);
      }
      applicationContext.getBeanFactory().registerSingleton("dropwizardConfiguration", dropwizardConfiguration);
      applicationContext.register(StandaloneConfig.class);
   }

   @Override
   public void start() throws Exception {
      applicationContext.refresh();
      applicationContext.start();
      applicationContext.registerShutdownHook();
   }

   @Override
   public void stop() throws Exception {
      applicationContext.stop();
   }
}
