package com.page5of4.dropwizard.activemq.example.publisher;

import com.page5of4.codon.BusConfiguration;
import com.page5of4.codon.Subscriber;
import com.page5of4.codon.activmq.discovery.ActiveMqNetworkManager;
import com.page5of4.codon.config.BusConfig;
import com.page5of4.codon.impl.TopologyConfiguration;
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
      return publisherConfiguration.getCodonConfiguration().createBusConfiguration(publisherConfiguration.getBrokerConfiguration());
   }

   @Bean
   public ActiveMqNetworkManager activeMqNetworkManager(BusConfiguration busConfiguration, TopologyConfiguration topologyConfiguration, PublisherConfiguration publisherConfiguration, Subscriber subscriber) {
      return new ActiveMqNetworkManager(busConfiguration, topologyConfiguration, subscriber, publisherConfiguration.getZooKeeper().getCurator(), publisherConfiguration.getBrokerConfiguration().createBroker());
   }
}
