package com.page5of4.dropwizard.activemq.example.subscriber;

import com.page5of4.codon.Bus;
import com.page5of4.codon.MessageHandler;
import com.page5of4.dropwizard.activemq.example.publisher.LaunchWorkMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@MessageHandler
public class LaunchWorkHandler {
   private static final Logger logger = LoggerFactory.getLogger(LaunchWorkHandler.class);
   private final Bus bus;

   @Autowired
   public LaunchWorkHandler(Bus bus) {
      super();
      this.bus = bus;
   }

   @MessageHandler
   public void handle(LaunchWorkMessage message) {
      logger.info("Received {}", message);
   }
}
