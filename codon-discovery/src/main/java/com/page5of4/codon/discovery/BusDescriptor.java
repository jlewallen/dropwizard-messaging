package com.page5of4.codon.discovery;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.google.common.collect.Lists;

import java.util.List;

@JsonRootName("bus")
public class BusDescriptor {
   public List<ListenerDescriptor> listeners = Lists.newArrayList();
   public String applicationName;
   public String brokerUrl;

   public List<ListenerDescriptor> getListeners() {
      return listeners;
   }

   public void addListener(String messageType, String address) {
      getListeners().add(new ListenerDescriptor(messageType, address));
   }

   public String getApplicationName() {
      return applicationName;
   }

   public void setApplicationName(String applicationName) {
      this.applicationName = applicationName;
   }

   public String getBrokerUrl() {
      return brokerUrl;
   }

   public void setBrokerUrl(String brokerUrl) {
      this.brokerUrl = brokerUrl;
   }

   public static class ListenerDescriptor {
      private String messageType;
      private String address;

      public String getMessageType() {
         return messageType;
      }

      public void setMessageType(String messageType) {
         this.messageType = messageType;
      }

      public String getAddress() {
         return address;
      }

      public void setAddress(String address) {
         this.address = address;
      }

      public ListenerDescriptor() {

      }

      public ListenerDescriptor(String messageType, String address) {
         this.messageType = messageType;
         this.address = address;
      }
   }

   @Override
   public String toString() {
      return "bus<" + getApplicationName() + ">";
   }
}
