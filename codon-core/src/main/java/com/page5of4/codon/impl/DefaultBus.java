package com.page5of4.codon.impl;

import com.page5of4.codon.Bus;
import com.page5of4.codon.EndpointAddress;
import com.page5of4.codon.Transport;
import com.page5of4.codon.subscriptions.messages.SubscribeMessage;
import com.page5of4.codon.subscriptions.messages.UnsubscribeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DefaultBus implements Bus {
   private static final Logger logger = LoggerFactory.getLogger(DefaultBus.class);
   private final BusContextProvider contextProvider;
   private final Transport transport;

   public DefaultBus(BusContextProvider contextProvider, Transport transport) {
      this.contextProvider = contextProvider;
      this.transport = transport;
   }

   @Override
   public <T> void publish(T message) {
      logger.info("Publish {}", message);
      for(EndpointAddress subscriber : contextProvider.currentContext().getSubscriptionStorage().findAllSubscribers(MessageUtils.getMessageType(message))) {
         transport.send(subscriber, message);
      }
   }

   @Override
   public <T> void send(T message) {
      logger.info("Send {}", message);
      transport.send(contextProvider.currentContext().getTopologyConfiguration().getOwner(message.getClass()), message);
   }

   @Override
   public <T> void sendLocal(T message) {
      logger.info("SendLocal {}", message);
      transport.send(contextProvider.currentContext().getTopologyConfiguration().getLocalAddressOf(message.getClass()), message);
   }

   @Override
   public <T> void send(EndpointAddress address, T message) {
      logger.info("Send {} -> {}", message, address);
      transport.send(address, message);
   }

   @Override
   public void subscribe(Class<?> messageType) {
      logger.info("Subscribing {}", messageType.getName());
      TopologyConfiguration topologyConfiguration = contextProvider.currentContext().getTopologyConfiguration();
      EndpointAddress local = topologyConfiguration.getLocalAddressOf(messageType);
      transport.send(topologyConfiguration.getSubscriptionAddressOf(messageType, SubscribeMessage.class), new SubscribeMessage(local.toString(), MessageUtils.getMessageType(messageType)));
   }

   @Override
   public void unsubscribe(Class<?> messageType) {
      logger.info("Unsubscribing {}", messageType.getName());
      TopologyConfiguration topologyConfiguration = contextProvider.currentContext().getTopologyConfiguration();
      EndpointAddress local = topologyConfiguration.getLocalAddressOf(messageType);
      transport.send(topologyConfiguration.getSubscriptionAddressOf(messageType, UnsubscribeMessage.class), new UnsubscribeMessage(local.toString(), MessageUtils.getMessageType(messageType)));
   }

   @Override
   public void listen(Class<?> messageType) {
      logger.info("Listen {}", messageType.getName());
      TopologyConfiguration topologyConfiguration = contextProvider.currentContext().getTopologyConfiguration();
      transport.listen(topologyConfiguration.getLocalAddressOf(messageType));
   }

   @Override
   public void unlisten(Class<?> messageType) {
      logger.info("Unlisten {}", messageType.getName());
      TopologyConfiguration topologyConfiguration = contextProvider.currentContext().getTopologyConfiguration();
      transport.unlisten(topologyConfiguration.getLocalAddressOf(messageType));
   }
}
