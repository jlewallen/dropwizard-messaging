package com.page5of4.codon.tests.support;

import com.page5of4.codon.BusConfiguration;
import com.page5of4.codon.PropertiesConfiguration;
import com.page5of4.codon.config.BusConfig;
import com.page5of4.codon.impl.JmsTransactionManagerConvention;
import com.page5of4.codon.impl.TransactionConvention;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SimpleBusConfigurationConfig extends BusConfig {
   @Bean
   @Override
   public BusConfiguration busConfiguration() {
      PropertiesConfiguration configuration = new PropertiesConfiguration("test", "activemq");
      configuration.put("bus.owner.com.page5of4.codon", "remote:remote.{messageType}");
      return configuration;
   }

   @Override
   public TransactionConvention transactionConvention() {
      return new JmsTransactionManagerConvention();
   }
}
