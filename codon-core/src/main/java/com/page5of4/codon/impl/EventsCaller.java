package com.page5of4.codon.impl;

import com.page5of4.codon.BusEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class EventsCaller implements BusEvents {
   private static final Logger logger = LoggerFactory.getLogger(EventsCaller.class);
   private Collection<BusEvents> busEventsCollection;

   public EventsCaller(Collection<BusEvents> busEventsCollection) {
      this.busEventsCollection = busEventsCollection;
   }

   @Override
   public void starting() {
      for(BusEvents listener : busEventsCollection) listener.starting();
   }

   @Override
   public void started() {
      for(BusEvents listener : busEventsCollection) listener.started();
   }

   @Override
   public void subscribe(Class<?> messageType) {
      for(BusEvents listener : busEventsCollection) listener.subscribe(messageType);
   }

   @Override
   public void unsubscribe(Class<?> messageType) {
      for(BusEvents listener : busEventsCollection) listener.unsubscribe(messageType);
   }

   @Override
   public void listen(Class<?> messageType) {
      for(BusEvents listener : busEventsCollection) listener.listen(messageType);
   }

   @Override
   public void unlisten(Class<?> messageType) {
      for(BusEvents listener : busEventsCollection) listener.unlisten(messageType);
   }

   @Override
   public void stopped() {
      for(BusEvents listener : busEventsCollection) listener.stopped();
   }
}
