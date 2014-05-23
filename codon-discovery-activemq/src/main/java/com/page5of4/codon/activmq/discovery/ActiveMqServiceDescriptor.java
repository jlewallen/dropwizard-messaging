package com.page5of4.codon.activmq.discovery;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("activemq")
public class ActiveMqServiceDescriptor {
   public String applicationName;
   public String instanceName;
   public String brokerUrl;

   public String getApplicationName() {
      return applicationName;
   }

   public void setApplicationName(String applicationName) {
      this.applicationName = applicationName;
   }

   public String getInstanceName() {
      return instanceName;
   }

   public void setInstanceName(String instanceName) {
      this.instanceName = instanceName;
   }

   public String getBrokerUrl() {
      return brokerUrl;
   }

   public void setBrokerUrl(String brokerUrl) {
      this.brokerUrl = brokerUrl;
   }

   @Override
   public String toString() {
      return "activemq<" + getApplicationName() + ">";
   }
}
