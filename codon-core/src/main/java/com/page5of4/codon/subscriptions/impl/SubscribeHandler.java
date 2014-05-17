package com.page5of4.codon.subscriptions.impl;

import com.page5of4.codon.AutomaticallySubscribe;
import com.page5of4.codon.MessageHandler;
import com.page5of4.codon.impl.BusContext;
import com.page5of4.codon.subscriptions.Subscription;
import com.page5of4.codon.subscriptions.SubscriptionStorage;
import com.page5of4.codon.subscriptions.messages.SubscribeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@MessageHandler(autoSubscribe = AutomaticallySubscribe.NEVER)
public class SubscribeHandler {
    private static final Logger logger = LoggerFactory.getLogger(SubscribeHandler.class);

    @MessageHandler
    public void handle(SubscribeMessage message, BusContext context) {
        SubscriptionStorage storage = context.getSubscriptionStorage();
        logger.info("Passing {} to {}", message, storage);
        storage.addSubscriptions(Collections.singleton(new Subscription(message.getAddress(), message.getMessageType())));
    }
}
