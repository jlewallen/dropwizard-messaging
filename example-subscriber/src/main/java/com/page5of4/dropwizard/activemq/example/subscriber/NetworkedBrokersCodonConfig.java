package com.page5of4.dropwizard.activemq.example.subscriber;

import com.page5of4.codon.Bus;
import com.page5of4.codon.BusConfiguration;
import com.page5of4.codon.PropertiesConfiguration;
import com.page5of4.codon.Subscriber;
import com.page5of4.codon.activmq.discovery.ActiveMqNetworkManager;
import com.page5of4.codon.config.BusConfig;
import com.page5of4.codon.impl.TopologyConfiguration;
import com.page5of4.dropwizard.discovery.LocalIpAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NetworkedBrokersCodonConfig extends BusConfig {
   @Autowired
   private SubscriberConfiguration subscriberConfiguration;

   @Bean
   public LaunchWorkHandler launchWorkHandler(Bus bus) {
      return new LaunchWorkHandler(bus);
   }

   @Bean
   @Override
   public BusConfiguration busConfiguration() {
      Integer port = subscriberConfiguration.getBrokerConfiguration().getPort();
      String localBrokerUrl = "tcp://" + LocalIpAddress.guessLocalIp().getHostAddress() + ":" + port;
      PropertiesConfiguration configuration = new PropertiesConfiguration("subscriber", localBrokerUrl);
      configuration.put("bus.owner.com.page5of4.dropwizard", "publisher:publisher.{messageType}");
      return configuration;
   }

   @Bean
   public ActiveMqNetworkManager activeMqNetworkManager(BusConfiguration busConfiguration, TopologyConfiguration topologyConfiguration, SubscriberConfiguration publisherConfiguration, Subscriber subscriber) {
      return new ActiveMqNetworkManager(busConfiguration, topologyConfiguration, subscriber, publisherConfiguration.getZooKeeperConfiguration().getCurator(), publisherConfiguration.getBrokerConfiguration().createBroker());
   }
}
