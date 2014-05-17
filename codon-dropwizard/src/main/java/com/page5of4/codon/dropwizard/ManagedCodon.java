package com.page5of4.codon.dropwizard;

import com.page5of4.codon.BusConfiguration;
import com.page5of4.codon.PropertiesConfiguration;
import com.page5of4.codon.config.InMemorySubscriptionStorageConfig;
import com.page5of4.codon.config.StandaloneConfig;
import com.page5of4.codon.impl.NullTransactionManagerConvention;
import io.dropwizard.lifecycle.Managed;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class ManagedCodon implements Managed {
   private AnnotationConfigApplicationContext applicationContext;

   @Override
   public void start() throws Exception {
      applicationContext = new AnnotationConfigApplicationContext();
      applicationContext.register(StandaloneConfig.class);
      applicationContext.register(SimpleBusConfigurationConfig.class);
      applicationContext.register(InMemorySubscriptionStorageConfig.class);
      applicationContext.register(TransactionConventionConfig.class);
      applicationContext.refresh();
      applicationContext.start();
      applicationContext.registerShutdownHook();
   }

   @Override
   public void stop() throws Exception {
      applicationContext.stop();
   }

   @Configuration
   public static class TransactionConventionConfig {
      @Bean
      public NullTransactionManagerConvention transactionManagerConvention() {
         return new NullTransactionManagerConvention();
      }
   }

   @Configuration
   public static class SimpleBusConfigurationConfig {
      @Bean
      public BusConfiguration busConfiguration() {
         PropertiesConfiguration configuration = new PropertiesConfiguration("test", "activemq");
         configuration.put("bus.owner.com.page5of4.codon", "remote:remote.{messageType}");
         return configuration;
      }
   }
}
