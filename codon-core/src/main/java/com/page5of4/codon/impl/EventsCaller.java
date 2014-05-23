package com.page5of4.codon.impl;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.page5of4.codon.BusEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Provider;
import java.util.Collection;

public class EventsCaller implements BusEvents {
   private static final Logger logger = LoggerFactory.getLogger(EventsCaller.class);
   private final Provider<Collection<BusEvents>> busEventsProvider;

   public EventsCaller(Provider<Collection<BusEvents>> busEventsProvider) {
      this.busEventsProvider = busEventsProvider;
   }

   private Iterable<BusEvents> busEvents() {
      return Iterables.filter(busEventsProvider.get(), new Predicate<BusEvents>() {
         @Override
         public boolean apply(BusEvents be) {
            return !(be instanceof EventsCaller);
         }
      });
   }

   @Override
   public void starting() {
      for(BusEvents listener : busEvents()) listener.starting();
   }

   @Override
   public void started() {
      for(BusEvents listener : busEvents()) listener.started();
   }

   @Override
   public void subscribe(Class<?> messageType) {
      for(BusEvents listener : busEvents()) listener.subscribe(messageType);
   }

   @Override
   public void unsubscribe(Class<?> messageType) {
      for(BusEvents listener : busEvents()) listener.unsubscribe(messageType);
   }

   @Override
   public void listen(Class<?> messageType) {
      for(BusEvents listener : busEvents()) listener.listen(messageType);
   }

   @Override
   public void unlisten(Class<?> messageType) {
      for(BusEvents listener : busEvents()) listener.unlisten(messageType);
   }

   @Override
   public void stopped() {
      for(BusEvents listener : busEvents()) listener.stopped();
   }
}
