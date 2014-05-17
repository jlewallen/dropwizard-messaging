package com.page5of4.dropwizard.activemq;

import io.dropwizard.lifecycle.Managed;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.store.memory.MemoryPersistenceAdapter;

import java.net.ServerSocket;

public class ActiveMqBroker implements Managed {
   private BrokerService broker;

   @Override
   public void start() throws Exception {
      ServerSocket s = new ServerSocket(0);
      Integer port = s.getLocalPort();
      s.close();

      port = 61616;

      broker = new BrokerService();
      broker.addConnector("tcp://127.0.0.1:" + port);
      // NetworkConnector connector = broker.addNetworkConnector("static://tcp://127.0.0.1:61616");
      // connector.setDuplex(true);
      broker.setUseJmx(false);
      broker.setPersistenceAdapter(new MemoryPersistenceAdapter());
      broker.start();

      ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory("vm://localhost?create=false");
   }

   @Override
   public void stop() throws Exception {
      broker.stop();
   }
}
