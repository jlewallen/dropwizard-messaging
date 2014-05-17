package com.page5of4.codon;

public interface BusConfiguration {
   String getApplicationName();

   String getOwnerAddress(String messageType);

   String getLocalAddress(String messageType);

   CommunicationConfiguration findCommunicationConfiguration(String name);

   ListenerConfiguration findListenerConfiguration(String name);

   public static class CommunicationConfiguration {
      private String componentName;

      public String getComponentName() {
         return componentName;
      }

      public void setComponentName(String componentName) {
         this.componentName = componentName;
      }

      @Override
      public String toString() {
         return "[componentName=" + componentName + "]";
      }
   }

}
