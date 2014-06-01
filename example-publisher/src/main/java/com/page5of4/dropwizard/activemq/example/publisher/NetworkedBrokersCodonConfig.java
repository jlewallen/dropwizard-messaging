package com.page5of4.dropwizard.activemq.example.publisher;

import com.page5of4.codon.Bus;
import com.page5of4.codon.BusConfiguration;
import com.page5of4.codon.Subscriber;
import com.page5of4.codon.activmq.discovery.ActiveMqNetworkManager;
import com.page5of4.codon.spring.config.BusConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NetworkedBrokersCodonConfig extends BusConfig {
   @Autowired
   private PublisherConfiguration publisherConfiguration;

   @Bean
   @Override
   public BusConfiguration busConfiguration() {
      return publisherConfiguration.getCodonConfiguration().createBusConfiguration();
   }

   @Bean
   public ActiveMqNetworkManager activeMqNetworkManager(BusConfiguration busConfiguration, PublisherConfiguration publisherConfiguration, Subscriber subscriber) {
      return new ActiveMqNetworkManager(busConfiguration, subscriber, publisherConfiguration.getZooKeeper().getCurator(), publisherConfiguration.getCodonConfiguration().getBroker().createBroker());
   }

   @Bean
   public DummyResource dummyResource(Bus bus) {
      return new DummyResource(bus);
   }
}
