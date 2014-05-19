package com.page5of4.dropwizard.activemq;

import io.dropwizard.lifecycle.Managed;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.store.memory.MemoryPersistenceAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;

public class ActiveMqBroker implements Managed {
   private static final Logger logger = LoggerFactory.getLogger(ActiveMqBroker.class);
   private final BrokerConfiguration configuration;
   private BrokerService broker;

   public ActiveMqBroker(BrokerConfiguration configuration) {
      this.configuration = configuration;
   }

   @Override
   public void start() throws Exception {
      String connector = "tcp://127.0.0.1:" + configuration.getPort();
      logger.info("Starting broker on {}", connector);
      broker = new BrokerService();
      broker.addConnector(connector);
      broker.setUseJmx(false);
      broker.setPersistenceAdapter(new MemoryPersistenceAdapter());
      broker.start();
   }

   @Override
   public void stop() throws Exception {
      broker.stop();
   }

   public static Integer getAvailablePort() {
      try {
         ServerSocket s = new ServerSocket(0);
         Integer port = s.getLocalPort();
         s.close();
         return port;
      }
      catch(IOException e) {
         throw new RuntimeException(e);
      }
   }
}
