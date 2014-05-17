package com.page5of4.codon;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Map.Entry;
import java.util.Properties;

public class PropertiesResourceConfiguration extends PropertiesConfiguration {
   public PropertiesResourceConfiguration(String applicationName, String localComponentName) {
      super(applicationName, localComponentName);
   }

   @Value("classpath:/META-INF/spring/application.properties")
   public void setPropertiesSource(Resource resource) {
      try {
         Properties loaded = new Properties();
         loaded.load(resource.getInputStream());
         for(Entry<Object, Object> e : loaded.entrySet()) {
            put((String)e.getKey(), (String)e.getValue());
         }
      }
      catch(IOException e) {
         throw new BusException("Error loading: " + resource, e);
      }
   }
}
