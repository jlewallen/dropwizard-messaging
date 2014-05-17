package com.page5of4.codon.camel;

import com.page5of4.codon.Bus;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public abstract class BusProcessor implements Processor {
   private final Bus bus;

   public Bus getBus() {
      return bus;
   }

   public BusProcessor(Bus bus) {
      super();
      this.bus = bus;
   }

   @Override
   public abstract void process(Exchange exchange) throws Exception;
}
