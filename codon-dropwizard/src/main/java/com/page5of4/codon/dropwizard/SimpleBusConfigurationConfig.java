package com.page5of4.codon.dropwizard;

import com.page5of4.codon.BusConfiguration;
import com.page5of4.codon.PropertiesConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SimpleBusConfigurationConfig {
   @Bean
   public BusConfiguration busConfiguration() {
      PropertiesConfiguration configuration = new PropertiesConfiguration("test", "activemq");
      configuration.put("bus.owner.com.page5of4.dropwizard", "remote:remote.{messageType}");
      return configuration;
   }
}
