package com.page5of4.codon.dropwizard;

import com.page5of4.codon.Bus;
import com.page5of4.codon.BusModule;
import io.dropwizard.Configuration;
import io.dropwizard.lifecycle.Managed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Collection;

public class ManagedCodon implements Managed {
   private static final Logger logger = LoggerFactory.getLogger(ManagedCodon.class);
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
      applicationContext.refresh();
   }

   @Override
   public void start() throws Exception {
      applicationContext.start();
      applicationContext.registerShutdownHook();
      applicationContext.getBean(BusModule.class).start();
   }

   @Override
   public void stop() throws Exception {
      applicationContext.getBean(BusModule.class).stop();
      applicationContext.stop();
   }

   public Bus getBus() {
      return applicationContext.getBean(Bus.class);
   }

   public CodonHealthCheck getHealthCheck() {
      return applicationContext.getBean(CodonHealthCheck.class);
   }

   public CodonResource getResource() {
      return applicationContext.getBean(CodonResource.class);
   }
}
