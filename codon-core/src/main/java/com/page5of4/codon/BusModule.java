package com.page5of4.codon;

import com.page5of4.codon.impl.EventsCaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Collection;

public class BusModule {
   private static final Logger logger = LoggerFactory.getLogger(BusModule.class);
   private final HandlerRegistry handlerRegistry;
   private final Bus bus;
   private final ModuleMode mode;
   private final EventsCaller busEventsRaiser;

   public HandlerRegistry getHandlerRegistry() {
      return handlerRegistry;
   }

   @Autowired
   public BusModule(HandlerRegistry handlerRegistry, Bus bus, ModuleMode mode, Collection<BusEvents> busEventsCollection) {
      super();
      this.handlerRegistry = handlerRegistry;
      this.bus = bus;
      this.mode = mode;
      this.busEventsRaiser = new EventsCaller(busEventsCollection);
   }

   @PostConstruct
   public void created() {
      if(mode == ModuleMode.STANDALONE) {
         open();
      }
   }

   @PreDestroy
   public void destroying() {
      if(mode == ModuleMode.STANDALONE) {
         close();
      }
   }

   public void open() {
      handlerRegistry.initialize();
      for(HandlerBinding binding : handlerRegistry.getBindings()) {
         logger.info("Preparing '{}'", binding.getMethod());
         Class<?> messageType = binding.getMessageType();
         if(binding.shouldSubscribe()) {
            bus.subscribe(binding.getMessageType());
         }
         bus.listen(messageType);
      }
      busEventsRaiser.started();
   }

   public void close() {
      for(HandlerBinding binding : handlerRegistry.getBindings()) {
         Class<?> messageType = binding.getMessageType();
         bus.unlisten(messageType);
      }
      busEventsRaiser.stopped();
   }

   public enum ModuleMode {
      STANDALONE,
      OSGI
   }
}
