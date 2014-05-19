package com.page5of4.dropwizard.activemq;

public class BrokerConfiguration {
   private Integer port;

   public BrokerConfiguration() {
      this.port = ActiveMqBroker.getAvailablePort();
   }

   public Integer getPort() {
      return port;
   }

   public void setPort(Integer port) {
      this.port = port;
   }
}
