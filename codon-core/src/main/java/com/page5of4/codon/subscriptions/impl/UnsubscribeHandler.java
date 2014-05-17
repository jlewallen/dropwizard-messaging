package com.page5of4.codon.subscriptions.impl;

import com.page5of4.codon.AutomaticallySubscribe;
import com.page5of4.codon.MessageHandler;
import com.page5of4.codon.impl.BusContext;
import com.page5of4.codon.subscriptions.Subscription;
import com.page5of4.codon.subscriptions.SubscriptionStorage;
import com.page5of4.codon.subscriptions.messages.UnsubscribeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

@MessageHandler(autoSubscribe = AutomaticallySubscribe.NEVER)
public class UnsubscribeHandler {
   private static final Logger logger = LoggerFactory.getLogger(UnsubscribeHandler.class);

   @MessageHandler
   public void handle(UnsubscribeMessage message, BusContext context) {
      SubscriptionStorage storage = context.getSubscriptionStorage();
      logger.info("Passing {} to {}", message, storage);
      storage.removeSubscriptions(Collections.singleton(new Subscription(message.getAddress(), message.getMessageType())));
   }
}
