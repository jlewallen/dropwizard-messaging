package com.page5of4.codon.impl;

public class ConstantBusContextProvider implements BusContextProvider {
   private final BusContext busContext;

   public ConstantBusContextProvider(BusContext busContext) {
      super();
      this.busContext = busContext;
   }

   @Override
   public BusContext currentContext() {
      return busContext;
   }
}
