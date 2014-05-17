package com.page5of4.codon.impl;

import org.springframework.transaction.PlatformTransactionManager;

import javax.jms.ConnectionFactory;

public interface TransactionConvention {

   PlatformTransactionManager locate(String name, ConnectionFactory connectionFactory);

   PlatformTransactionManager locate(String name);

}
