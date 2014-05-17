package com.page5of4.codon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class BusModule {
   private static final Logger logger = LoggerFactory.getLogger(BusModule.class);
   private final HandlerRegistry handlerRegistry;
   private final Bus bus;
   private final ModuleMode mode;

   public HandlerRegistry getHandlerRegistry() {
      return handlerRegistry;
   }

   @Autowired
   public BusModule(HandlerRegistry handlerRegistry, Bus bus, ModuleMode mode) {
      super();
      this.handlerRegistry = handlerRegistry;
      this.bus = bus;
      this.mode = mode;
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
   }

   public void close() {
      for(HandlerBinding binding : handlerRegistry.getBindings()) {
         Class<?> messageType = binding.getMessageType();
         bus.unlisten(messageType);
      }
   }

   public enum ModuleMode {
      STANDALONE,
      OSGI
   }
}
