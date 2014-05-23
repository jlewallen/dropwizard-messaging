package com.page5of4.codon;

import java.util.HashMap;
import java.util.Map;

public class PropertiesConfiguration implements BusConfiguration {
   public static final String ACTIVEMQ_COMPONENT_NAME = "activemq";
   private static final String BUS_OWNER = "bus.owner.";
   private static final String LOCAL_ADDRESS_FORMAT = "%s:%s.{messageType}";
   private final Map<String, String> properties = new HashMap<String, String>();
   private final String applicationName;
   private final String localBrokerUrl;

   @Override
   public String getApplicationName() {
      return applicationName;
   }

   public String getLocalBrokerUrl() {
      return localBrokerUrl;
   }

   public void addProperties(Map<String, String> properties) {
      this.properties.putAll(properties);
   }

   public void put(String key, String value) {
      this.properties.put(key, value);
   }

   public void setOwner(String owned, String by) {
      put(BUS_OWNER + owned, by);
   }

   public PropertiesConfiguration(String applicationName, String localBrokerUrl) {
      super();
      this.applicationName = applicationName;
      this.localBrokerUrl = localBrokerUrl;
      if(applicationName == null || applicationName.length() == 0) {
         throw new BusException("Application name is required.");
      }
   }

   @Override
   public String getOwnerAddress(String messageType) {
      String selected = null;
      for(Map.Entry<String, String> entry : properties.entrySet()) {
         if(entry.getKey().startsWith(BUS_OWNER)) {
            String keysPackage = entry.getKey().replace(BUS_OWNER, "");
            if(messageType.startsWith(keysPackage) && (selected == null || selected.length() < keysPackage.length())) {
               selected = entry.getKey();
            }
         }
      }
      if(selected != null) {
         return properties.get(selected);
      }
      throw new BusException(String.format("Unable to locate Owner for '%s'", messageType));
   }

   @Override
   public String getLocalAddress(String messageType) {
      return String.format(LOCAL_ADDRESS_FORMAT, applicationName, applicationName);
   }

   @Override
   public CommunicationConfiguration findCommunicationConfiguration(String name) {
      return new CommunicationConfiguration() {
         {
            setComponentName(ACTIVEMQ_COMPONENT_NAME);
            setUrl(getLocalBrokerUrl());
         }
      };
   }

   @Override
   public ListenerConfiguration findListenerConfiguration(String name) {
      Integer concurrency = 2;
      ListenerConfiguration cfg = new ListenerConfiguration();
      cfg.setId("listen:" + name);
      cfg.setConcurrency(concurrency);
      cfg.setTransacted(false);
      cfg.setListenAddress(name + "?concurrentConsumers=" + concurrency);
      cfg.setPoisonAddress(name + ".posion");
      return cfg;
   }
}
