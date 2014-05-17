package com.page5of4.codon.tests.integration;

import java.io.Serializable;

public class MessageAMessage implements Serializable {
   private static final long serialVersionUID = 1L;
   private String name;

   public MessageAMessage(String name) {
      super();
      this.name = name;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }
}