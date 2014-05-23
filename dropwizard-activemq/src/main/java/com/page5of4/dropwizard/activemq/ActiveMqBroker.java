package com.page5of4.dropwizard.activemq;

import io.dropwizard.lifecycle.Managed;
import org.apache.activemq.broker.BrokerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActiveMqBroker implements Managed {
   private static final Logger logger = LoggerFactory.getLogger(ActiveMqBroker.class);
   private final BrokerConfiguration configuration;
   private final BrokerService broker;

   public ActiveMqBroker(BrokerConfiguration configuration) {
      this.configuration = configuration;
      this.broker = configuration.createBroker();
   }

   @Override
   public void start() throws Exception {
      logger.info("Starting broker on {}", configuration.getBrokerListenUrl());
      broker.start();
   }

   @Override
   public void stop() throws Exception {
      logger.info("Stopping broker on {}", configuration.getBrokerListenUrl());
      broker.stop();
   }
}
