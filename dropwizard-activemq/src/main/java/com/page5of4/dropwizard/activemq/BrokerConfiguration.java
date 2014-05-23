package com.page5of4.dropwizard.activemq;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.store.memory.MemoryPersistenceAdapter;

import java.io.IOException;
import java.net.ServerSocket;

public class BrokerConfiguration {
   @JsonProperty
   private Integer port;
   private BrokerService broker;

   public BrokerConfiguration() {
      this.port = getAvailablePort();
   }

   public Integer getPort() {
      return port;
   }

   public String getBrokerListenUrl() {
      return "tcp://0.0.0.0:" + getPort();
   }

   public void setPort(Integer port) {
      this.port = port;
   }

   public BrokerService createBroker() {
      if(broker == null) {
         try {
            broker = new BrokerService();
            broker.addConnector(getBrokerListenUrl());
            broker.setUseJmx(true);
            broker.setPersistenceAdapter(new MemoryPersistenceAdapter());
         }
         catch(Exception e) {
            throw new RuntimeException(e);
         }
      }
      return broker;
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
