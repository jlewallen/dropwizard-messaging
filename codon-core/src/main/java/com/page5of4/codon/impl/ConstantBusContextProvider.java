package com.page5of4.codon.impl;

import org.springframework.beans.factory.annotation.Autowired;

public class ConstantBusContextProvider implements BusContextProvider {
   private final BusContext busContext;

   @Autowired
   public ConstantBusContextProvider(BusContext busContext) {
      super();
      this.busContext = busContext;
   }

   @Override
   public BusContext currentContext() {
      return busContext;
   }
}
