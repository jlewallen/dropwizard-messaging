package com.page5of4.codon.camel;

import com.page5of4.codon.BusException;
import com.page5of4.codon.HandlerBinding;
import com.page5of4.codon.HandlerRegistry;
import com.page5of4.codon.impl.BusContextProvider;
import com.page5of4.codon.impl.InstanceResolver;
import com.page5of4.codon.impl.MessageUtils;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class InvokeHandlerProcessor implements Processor {
   private static final Logger logger = LoggerFactory.getLogger(InvokeHandlerProcessor.class);
   private final HandlerRegistry handlerRegistry;
   private final BusContextProvider contextProvider;
   private final NoHandlersBehavior noHandlersBehavior;

   public InvokeHandlerProcessor(HandlerRegistry handlerRegistry, BusContextProvider contextProvider, InstanceResolver resolver) {
      super();
      this.handlerRegistry = handlerRegistry;
      this.contextProvider = contextProvider;
      this.noHandlersBehavior = NoHandlersBehavior.THROW;
   }

   @Override
   public void process(Exchange exchange) throws Exception {
      Message message = exchange.getIn();
      Object body = message.getBody();
      Class<?> bodyClass = body.getClass();
      String messageType = MessageUtils.getMessageType(body);
      Map<String, Object> headers = message.getHeaders();
      if(headers.containsKey(DefaultCamelTransport.MESSAGE_TYPE_KEY)) {
         messageType = headers.get(DefaultCamelTransport.MESSAGE_TYPE_KEY).toString();
      }
      else {
         logger.warn("No message type on message, assuming no conversion necessary: '{}'", messageType);
      }

      logger.debug(String.format("Processing: %s/%s '%s'", messageType, bodyClass.getName(), body));
      List<HandlerBinding> bindings = handlerRegistry.getBindingsFor(bodyClass);
      for(HandlerBinding binding : bindings) {
         logger.debug("Invoking {}", binding.getMethod());
         try {
            binding.dispatch(body, exchange, contextProvider);
            logger.debug("Pass {}", binding.getMethod());
         }
         catch(Throwable e) {
            exchange.setException(e);
            break;
         }
      }
      if(bindings.isEmpty()) {
         String noHandlersMessage = String.format("No handlers registered for %s/%s", messageType, bodyClass.getName());
         switch(noHandlersBehavior) {
         case THROW:
            throw new BusException(noHandlersMessage);
         case WARN:
            logger.warn(noHandlersMessage);
            break;
         }
      }
   }

   public enum NoHandlersBehavior {
      THROW,
      WARN
   }
}
