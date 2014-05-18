package com.page5of4.dropwizard.activemq;

import io.dropwizard.lifecycle.Managed;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.store.memory.MemoryPersistenceAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActiveMqBroker implements Managed {
   private static final Logger logger = LoggerFactory.getLogger(ActiveMqBroker.class);
   private BrokerService broker;

   @Override
   public void start() throws Exception {
      // ServerSocket s = new ServerSocket(0);
      // Integer port = s.getLocalPort();
      // s.close();

      String connector = "tcp://127.0.0.1:61616";
      logger.info("Starting broker on {}", connector);
      broker = new BrokerService();
      broker.addConnector(connector);
      broker.setUseJmx(false);
      broker.setPersistenceAdapter(new MemoryPersistenceAdapter());
      broker.start();

      // ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory("vm://localhost?create=false");
   }

   @Override
   public void stop() throws Exception {
      broker.stop();
   }

   public static class Marker {

   }
}
