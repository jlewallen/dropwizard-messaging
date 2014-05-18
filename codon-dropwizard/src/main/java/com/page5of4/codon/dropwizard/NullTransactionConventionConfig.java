package com.page5of4.codon.dropwizard;

import com.page5of4.codon.impl.NullTransactionManagerConvention;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NullTransactionConventionConfig {
   @Bean
   public NullTransactionManagerConvention transactionManagerConvention() {
      return new NullTransactionManagerConvention();
   }
}
