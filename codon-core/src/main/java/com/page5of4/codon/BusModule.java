package com.page5of4.codon;

import com.page5of4.codon.impl.EventsCaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BusModule {
   private static final Logger logger = LoggerFactory.getLogger(BusModule.class);
   private final HandlerRegistry handlerRegistry;
   private final Bus bus;
   private final Subscriber subscriber;
   private final EventsCaller busEventsRaiser;

   public BusModule(HandlerRegistry handlerRegistry, Bus bus, Subscriber subscriber, EventsCaller eventsCaller) {
      super();
      this.handlerRegistry = handlerRegistry;
      this.bus = bus;
      this.subscriber = subscriber;
      this.busEventsRaiser = eventsCaller;
   }

   public void start() {
      handlerRegistry.initialize();
      busEventsRaiser.starting();
      subscriber.subscribeAll();
      busEventsRaiser.started();
   }

   public void stop() {
      for(HandlerBinding binding : handlerRegistry.getBindings()) {
         Class<?> messageType = binding.getMessageType();
         bus.unlisten(messageType);
      }
      busEventsRaiser.stopped();
   }
}
