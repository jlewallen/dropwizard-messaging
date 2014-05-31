package com.page5of4.codon.transactions;

import com.page5of4.codon.BusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.jms.ConnectionFactory;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JmsTransactionManagerConvention implements TransactionConvention {
   private static final Logger logger = LoggerFactory.getLogger(JmsTransactionManagerConvention.class);
   private final Map<String, PlatformTransactionManager> cache = new ConcurrentHashMap<String, PlatformTransactionManager>();

   @Override
   public PlatformTransactionManager locate(String name, ConnectionFactory connectionFactory) {
      String key = getKey(name);
      if(cache.containsKey(key)) {
         logger.info("Returning cached PlatformTransactionManager for '{}' ('{}')", name, key);
         return cache.get(key);
      }
      logger.info("Creating PlatformTransactionManager for '{}'", name);
      JmsTransactionManager manager = new JmsTransactionManager(connectionFactory);
      cache.put(key, manager);
      return manager;
   }

   @Override
   public PlatformTransactionManager locate(String name) {
      String key = getKey(name);
      if(cache.containsKey(key)) {
         logger.info("Returning cached PlatformTransactionManager for '{}' ('{}')", name, key);
         return cache.get(key);
      }
      throw new BusException(String.format("No PlatformTransactionManager available for '%s'", name));
   }

   public String getKey(String name) {
      return name;
   }
}
