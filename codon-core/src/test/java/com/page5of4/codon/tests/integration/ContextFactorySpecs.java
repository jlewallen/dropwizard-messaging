package com.page5of4.codon.tests.integration;

import com.page5of4.codon.BusConfiguration;
import com.page5of4.codon.PropertiesConfiguration;
import com.page5of4.codon.config.BusConfig;
import com.page5of4.codon.impl.JmsTransactionManagerConvention;
import com.page5of4.codon.impl.TransactionConvention;
import com.page5of4.codon.tests.support.EmbeddedActiveMqBrokerConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class ContextFactorySpecs {
   @Test
   public void when_creating_context() {
      AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
      applicationContext.register(EmbeddedActiveMqBrokerConfig.class);
      applicationContext.register(SimpleBusConfigurationConfig.class);
      applicationContext.refresh();
      applicationContext.destroy();
   }

   @Configuration
   public static class SimpleBusConfigurationConfig extends BusConfig {
      @Bean
      @Override
      public BusConfiguration busConfiguration() {
         return new PropertiesConfiguration("test", "mock");
      }

      @Override
      public TransactionConvention transactionConvention() {
         return new JmsTransactionManagerConvention();
      }
   }
}
