package com.page5of4.codon;

public interface BusEvents {
   void starting(); // This could probably go away, and just respond to the first subscribe? -jlewallen
   void started();
   void subscribe(Class<?> messageType);
   void unsubscribe(Class<?> messageType);
   void listen(Class<?> messageType);
   void unlisten(Class<?> messageType);
   void stopped();
}
