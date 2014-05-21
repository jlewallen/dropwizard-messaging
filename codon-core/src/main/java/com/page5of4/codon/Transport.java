package com.page5of4.codon;

public interface Transport {
   void start();

   void send(EndpointAddress address, Object message);

   void listen(EndpointAddress address);

   void unlisten(EndpointAddress address);

   void stop();
}
