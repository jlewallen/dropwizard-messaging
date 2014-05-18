package com.page5of4.codon;

public interface BusConfiguration {
   String getApplicationName();

   String getOwnerAddress(String messageType);

   String getLocalAddress(String messageType);

   CommunicationConfiguration findCommunicationConfiguration(String name);

   ListenerConfiguration findListenerConfiguration(String name);

}
