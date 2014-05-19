package com.page5of4.codon.dropwizard;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.google.common.collect.Lists;

import java.util.List;

@JsonRootName("bus")
public class BusDescriptor {
   public List<ListenerDescriptor> listeners = Lists.newArrayList();

   public List<ListenerDescriptor> getListeners() {
      return listeners;
   }

   public void setListeners(List<ListenerDescriptor> listeners) {
      this.listeners = listeners;
   }

   public void addListener(String messageType, String address) {
      getListeners().add(new ListenerDescriptor(messageType, address));
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
}
