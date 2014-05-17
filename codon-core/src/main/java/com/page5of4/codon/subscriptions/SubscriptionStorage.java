package com.page5of4.codon.subscriptions;

import com.page5of4.codon.EndpointAddress;

import java.util.Collection;
import java.util.List;

public interface SubscriptionStorage {
   List<Subscription> findAllSubscriptions();

   List<EndpointAddress> findAllSubscribers(String messageType);

   void addSubscriptions(Collection<Subscription> subscriptions);

   void removeSubscriptions(Collection<Subscription> subscriptions);
}
