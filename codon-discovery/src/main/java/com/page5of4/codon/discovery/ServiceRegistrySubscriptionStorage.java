package com.page5of4.codon.discovery;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.page5of4.codon.EndpointAddress;
import com.page5of4.codon.subscriptions.Subscription;
import com.page5of4.codon.subscriptions.SubscriptionStorage;
import com.page5of4.codon.subscriptions.impl.SubscriptionUtils;
import com.page5of4.dropwizard.discovery.zookeeper.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ServiceRegistrySubscriptionStorage implements SubscriptionStorage {
   private static final Logger logger = LoggerFactory.getLogger(ServiceRegistrySubscriptionStorage.class);

   public ServiceRegistrySubscriptionStorage() {
      LoadingCache<Object, Collection<Subscription>> subscriptions = CacheBuilder.newBuilder().
         expireAfterWrite(500, TimeUnit.MILLISECONDS).
         build(
            new CacheLoader<Object, Collection<Subscription>>() {
               @Override
               public Collection<Subscription> load(Object key) throws Exception {
                  return null;
               }
            }
         );
   }

   @Override
   public Collection<Subscription> findAllSubscriptions() {
      Collection<Subscription> subscriptions = Lists.newArrayList();
      Collection<BusDescriptor> busDescriptors = ServiceRegistry.get().getServices(BusDescriptor.class);
      for(BusDescriptor descriptor : busDescriptors) {
         for(BusDescriptor.ListenerDescriptor listenerDescriptor : descriptor.getListeners()) {
            String address = listenerDescriptor.getAddress();
            String messageType = listenerDescriptor.getMessageType();
            subscriptions.add(new Subscription(address, messageType));
         }
      }
      return subscriptions;
   }

   @Override
   public List<EndpointAddress> findAllSubscribers(String messageType) {
      return SubscriptionUtils.filter(findAllSubscriptions(), messageType);
   }

   @Override
   public void addSubscriptions(Collection<Subscription> subscriptions) {
      throw new RuntimeException();
   }

   @Override
   public void removeSubscriptions(Collection<Subscription> subscriptions) {
      throw new RuntimeException();
   }
}
