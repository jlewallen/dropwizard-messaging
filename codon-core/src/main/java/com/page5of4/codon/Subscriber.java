package com.page5of4.codon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Subscriber {
   private static final Logger logger = LoggerFactory.getLogger(Subscriber.class);
   private final HandlerRegistry handlerRegistry;
   private final Bus bus;

   public Subscriber(HandlerRegistry handlerRegistry, Bus bus) {
      this.handlerRegistry = handlerRegistry;
      this.bus = bus;
   }

   public void subscribeAll() {
      for(HandlerBinding binding : handlerRegistry.getBindings()) {
         Class<?> messageType = binding.getMessageType();
         if(binding.shouldSubscribe()) {
            bus.subscribe(binding.getMessageType());
         }
         bus.listen(messageType);
      }
   }
}
