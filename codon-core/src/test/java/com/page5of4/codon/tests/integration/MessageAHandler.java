package com.page5of4.codon.tests.integration;

import com.page5of4.codon.AutomaticallySubscribe;
import com.page5of4.codon.MessageHandler;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.model.ModelCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicLong;

@MessageHandler(autoSubscribe = AutomaticallySubscribe.NEVER)
public class MessageAHandler {
   private static final Logger logger = LoggerFactory.getLogger(MessageHandler.class);
   private final ProducerTemplate producer;
   private final AtomicLong counter = new AtomicLong();
   private final List<Callable<Object>> calls = new ArrayList<Callable<Object>>();

   @Autowired
   public MessageAHandler(ModelCamelContext camelContext) {
      super();
      this.producer = camelContext.createProducerTemplate();
   }

   @MessageHandler
   public void handle(MessageAMessage message, Exchange exchange) throws Exception {
      long pass = counter.incrementAndGet();
      logger.info("Entering: {}", pass);
      for(Callable<Object> c : calls) {
         c.call();
      }
      logger.info("Leaving: {}", pass);
   }

   public void when(Callable<Object> callable) {
      calls.clear();
      calls.add(callable);
   }
}
