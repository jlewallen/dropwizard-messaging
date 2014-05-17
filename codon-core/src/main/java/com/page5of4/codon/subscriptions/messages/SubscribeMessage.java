package com.page5of4.codon.subscriptions.messages;

import java.io.Serializable;

public class SubscribeMessage implements Serializable {
   private static final long serialVersionUID = 1L;

   private final String address;
   private final String messageType;

   public String getAddress() {
      return address;
   }

   public String getMessageType() {
      return messageType;
   }

   public SubscribeMessage(String address, String messageType) {
      super();
      this.address = address;
      this.messageType = messageType;
   }

   @Override
   public String toString() {
      return "SubscribeMessage [address=" + address + ", messageType=" + messageType + "]";
   }
}
