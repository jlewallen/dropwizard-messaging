package com.page5of4.codon.dropwizard;

import com.page5of4.codon.config.InMemorySubscriptionStorageConfig;
import com.page5of4.codon.config.StandaloneConfig;
import io.dropwizard.lifecycle.Managed;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Collection;

public class ManagedCodon implements Managed {
   private final AnnotationConfigApplicationContext applicationContext;
   private final Collection<Class<?>> configurationClasses;

   public ManagedCodon(Collection<Class<?>> configurationClasses) {
      this.configurationClasses = configurationClasses;
      this.applicationContext = new AnnotationConfigApplicationContext();
   }

   @Override
   public void start() throws Exception {
      for(Class<?> configurationClass : configurationClasses) {
         applicationContext.register(configurationClass);
      }
      applicationContext.register(StandaloneConfig.class);
      applicationContext.register(SimpleBusConfigurationConfig.class);
      applicationContext.register(InMemorySubscriptionStorageConfig.class);
      applicationContext.register(NullTransactionConventionConfig.class);
      applicationContext.refresh();
      applicationContext.start();
      applicationContext.registerShutdownHook();
   }

   @Override
   public void stop() throws Exception {
      applicationContext.stop();
   }
}
