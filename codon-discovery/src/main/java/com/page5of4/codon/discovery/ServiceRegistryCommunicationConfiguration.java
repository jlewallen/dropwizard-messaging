package com.page5of4.codon.discovery;

import com.page5of4.codon.CommunicationConfiguration;
import com.page5of4.codon.PropertiesConfiguration;
import com.page5of4.dropwizard.discovery.zookeeper.ServiceRegistry;

import java.util.Collection;

public class ServiceRegistryCommunicationConfiguration {
   public CommunicationConfiguration findCommunicationConfiguration(String name) {
      Collection<BusDescriptor> busDescriptors = ServiceRegistry.get().getServices(BusDescriptor.class);
      for(final BusDescriptor descriptor : busDescriptors) {
         if(descriptor.getApplicationName().equals(name)) {
            return new CommunicationConfiguration() {
               {
                  setComponentName(PropertiesConfiguration.ACTIVEMQ_COMPONENT_NAME);
                  setUrl(descriptor.getBrokerUrl());
               }
            };
         }
      }
      throw new RuntimeException("No such application: " + name);
   }
}
