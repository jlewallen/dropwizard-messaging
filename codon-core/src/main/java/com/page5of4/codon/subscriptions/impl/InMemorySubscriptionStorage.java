package com.page5of4.codon.subscriptions.impl;

import com.page5of4.codon.EndpointAddress;
import com.page5of4.codon.subscriptions.Subscription;
import com.page5of4.codon.subscriptions.SubscriptionStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InMemorySubscriptionStorage implements SubscriptionStorage {
   private final Set<Subscription> subscriptions = new HashSet<Subscription>();

   @Override
   public List<Subscription> findAllSubscriptions() {
      return new ArrayList<Subscription>(subscriptions);
   }

   @Override
   public List<EndpointAddress> findAllSubscribers(String messageType) {
      return SubscriptionUtils.filter(findAllSubscriptions(), messageType);
   }

   @Override
   public void addSubscriptions(Collection<Subscription> subscriptions) {
      this.subscriptions.addAll(subscriptions);
   }

   @Override
   public void removeSubscriptions(Collection<Subscription> subscriptions) {
      this.subscriptions.removeAll(subscriptions);
   }
}
