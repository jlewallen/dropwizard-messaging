package com.page5of4.dropwizard.activemq;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BrokerConfiguration {
   @JsonProperty
   private Integer port;

   public BrokerConfiguration() {
      this.port = ActiveMqBroker.getAvailablePort();
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
}
