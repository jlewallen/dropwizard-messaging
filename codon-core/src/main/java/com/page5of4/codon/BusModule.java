package com.page5of4.codon;

import com.page5of4.codon.impl.EventsCaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class BusModule {
   private static final Logger logger = LoggerFactory.getLogger(BusModule.class);
   private final HandlerRegistry handlerRegistry;
   private final Bus bus;
   private final Transport transport;
   private final EventsCaller busEventsRaiser;

   public HandlerRegistry getHandlerRegistry() {
      return handlerRegistry;
   }

   public BusModule(HandlerRegistry handlerRegistry, Bus bus, Transport transport, Collection<BusEvents> busEventsCollection) {
      super();
      this.handlerRegistry = handlerRegistry;
      this.bus = bus;
      this.transport = transport;
      this.busEventsRaiser = new EventsCaller(busEventsCollection);
   }

   public void start() {
      handlerRegistry.initialize();
      busEventsRaiser.starting();
      subscribeAll();
      busEventsRaiser.started();
   }

   public void subscribeAll() {
      for(HandlerBinding binding : handlerRegistry.getBindings()) {
         logger.info("Preparing '{}'", binding.getMethod());
         Class<?> messageType = binding.getMessageType();
         if(binding.shouldSubscribe()) {
            bus.subscribe(binding.getMessageType());
         }
         bus.listen(messageType);
      }
   }

   public void stop() {
      for(HandlerBinding binding : handlerRegistry.getBindings()) {
         Class<?> messageType = binding.getMessageType();
         bus.unlisten(messageType);
      }
      busEventsRaiser.stopped();
   }
}
