package com.page5of4.codon.config;

import com.page5of4.codon.BusConfiguration;
import com.page5of4.codon.camel.AutomaticTransactionPolicy;
import com.page5of4.codon.impl.JmsTransactionManagerConvention;
import com.page5of4.codon.impl.TransactionConvention;
import org.apache.camel.spi.TransactedPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JmsTransactionConventionConfig {
    @Autowired
    private BusConfiguration configuration;

    @Bean
    public TransactionConvention transactionConvention() {
        return new JmsTransactionManagerConvention();
    }

    @Bean
    public TransactedPolicy transactedPolicy() {
        return new AutomaticTransactionPolicy(configuration, transactionConvention());
    }
}
