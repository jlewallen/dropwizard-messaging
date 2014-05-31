package com.page5of4.dropwizard.activemq.example.publisher;

import com.page5of4.codon.BusConfiguration;
import com.page5of4.codon.CommunicationConfiguration;
import com.page5of4.codon.PropertiesConfiguration;
import com.page5of4.codon.spring.config.BusConfig;
import com.page5of4.codon.discovery.BusDescriptorPublisher;
import com.page5of4.codon.discovery.ServiceRegistryCommunicationConfiguration;
import com.page5of4.codon.discovery.ServiceRegistrySubscriptionStorage;
import com.page5of4.codon.impl.TopologyConfiguration;
import com.page5of4.codon.subscriptions.SubscriptionStorage;
import com.page5of4.dropwizard.discovery.LocalIpAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NonNetworkedBrokersCodonConfig extends BusConfig {
   @Autowired
   private PublisherConfiguration publisherConfiguration;

   @Bean
   @Override
   public BusConfiguration busConfiguration() {
      Integer port = publisherConfiguration.getBrokerConfiguration().getPort();
      String localBrokerUrl = "tcp://" + LocalIpAddress.guessLocalIp().getHostAddress() + ":" + port;
      final ServiceRegistryCommunicationConfiguration serviceRegistryCommunicationConfiguration = new ServiceRegistryCommunicationConfiguration();
      PropertiesConfiguration configuration = new PropertiesConfiguration("publisher", localBrokerUrl) {
         @Override
         public CommunicationConfiguration findCommunicationConfiguration(String name) {
            if(name.equals("publisher")) {
               return super.findCommunicationConfiguration(name);
            }
            return serviceRegistryCommunicationConfiguration.findCommunicationConfiguration(name);
         }
      };
      configuration.put("bus.owner.com.page5of4.dropwizard", "remote:remote.{messageType}");
      return configuration;
   }

   @Bean
   public BusDescriptorPublisher busDescriptorPublisher(BusConfiguration busConfiguration, TopologyConfiguration topologyConfiguration, PublisherConfiguration publisherConfiguration) {
      return new BusDescriptorPublisher(busConfiguration, topologyConfiguration, publisherConfiguration.getZooKeeper().getCurator());
   }

   @Override
   public SubscriptionStorage subscriptionStorage() {
      return new ServiceRegistrySubscriptionStorage();
   }
}
