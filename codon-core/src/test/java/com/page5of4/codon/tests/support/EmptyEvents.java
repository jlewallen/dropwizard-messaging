package com.page5of4.codon.tests.support;

import com.page5of4.codon.BusEvents;

public class EmptyEvents implements BusEvents {
   @Override
   public void starting() {

   }

   @Override
   public void started() {

   }

   @Override
   public void subscribe(Class<?> messageType) {

   }

   @Override
   public void unsubscribe(Class<?> messageType) {

   }

   @Override
   public void listen(Class<?> messageType) {

   }

   @Override
   public void unlisten(Class<?> messageType) {

   }

   @Override
   public void stopped() {

   }
}
