package com.page5of4.codon.impl;

import org.springframework.transaction.PlatformTransactionManager;

import javax.jms.ConnectionFactory;

public class NullTransactionManagerConvention implements TransactionConvention {
    @Override
    public PlatformTransactionManager locate(String name, ConnectionFactory connectionFactory) {
        return null;
    }

    @Override
    public PlatformTransactionManager locate(String name) {
        return null;
    }
}
