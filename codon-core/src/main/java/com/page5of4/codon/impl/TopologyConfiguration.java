package com.page5of4.codon.impl;

import com.page5of4.codon.BusConfiguration;
import com.page5of4.codon.EndpointAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
public class TopologyConfiguration {
   private static final String MESSAGE_TYPE = "messageType";
   private final BusConfiguration configuration;

   @Autowired
   public TopologyConfiguration(BusConfiguration configuration) {
      super();
      this.configuration = configuration;
   }

   public EndpointAddress getLocalAddressOf(Class<?> message) {
      String messageType = MessageUtils.getMessageType(message);
      String localAddress = configuration.getLocalAddress(messageType);
      return new EndpointAddress(replace(localAddress, Collections.singletonMap(MESSAGE_TYPE, messageType)));
   }

   public EndpointAddress getOwner(Class<?> message) {
      String messageType = MessageUtils.getMessageType(message);
      String ownerAddress = configuration.getOwnerAddress(messageType);
      return new EndpointAddress(replace(ownerAddress, Collections.singletonMap(MESSAGE_TYPE, messageType)));
   }

   public EndpointAddress getSubscriptionAddressOf(Class<?> otherMessage, Class<?> message) {
      String ownerAddress = configuration.getOwnerAddress(MessageUtils.getMessageType(otherMessage));
      return new EndpointAddress(replace(ownerAddress, Collections.singletonMap(MESSAGE_TYPE, MessageUtils.getMessageType(message))));
   }

   private String replace(String template, Map<String, String> values) {
      String value = template;
      for(Map.Entry<String, String> entry : values.entrySet()) {
         value = value.replace("{" + entry.getKey() + "}", entry.getValue());
      }
      return value;
   }
}
