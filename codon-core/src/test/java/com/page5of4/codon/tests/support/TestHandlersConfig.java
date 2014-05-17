package com.page5of4.codon.tests.support;

import com.page5of4.codon.tests.integration.MessageAHandler;
import org.apache.camel.model.ModelCamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestHandlersConfig {
   @Autowired
   private ModelCamelContext camelContext;

   @Bean
   public MessageAHandler messageAHandler() {
      return new MessageAHandler(camelContext);
   }
}