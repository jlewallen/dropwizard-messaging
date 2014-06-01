package com.page5of4.dropwizard.activemq.example.subscriber;

import com.page5of4.codon.Bus;
import com.page5of4.codon.BusConfiguration;
import com.page5of4.codon.PropertiesConfiguration;
import com.page5of4.codon.spring.config.BusConfig;
import com.page5of4.codon.discovery.BusDescriptorPublisher;
import com.page5of4.codon.impl.TopologyConfiguration;
import com.page5of4.dropwizard.discovery.LocalIpAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NonNewtorkedBrokersCodonConfig extends BusConfig {
   @Autowired
   private SubscriberConfiguration subscriberConfiguration;

   @Bean
   public LaunchWorkHandler launchWorkHandler(Bus bus) {
      return new LaunchWorkHandler(bus);
   }

   @Bean
   @Override
   public BusConfiguration busConfiguration() {
      Integer port = subscriberConfiguration.getCodonConfiguration().getBroker().getPort();
      String localBrokerUrl = "tcp://" + LocalIpAddress.guessLocalIp().getHostAddress() + ":" + port;
      PropertiesConfiguration configuration = new PropertiesConfiguration("subscriber", localBrokerUrl);
      return configuration;
   }

   @Bean
   public BusDescriptorPublisher busDescriptorPublisher(BusConfiguration busConfiguration, TopologyConfiguration topologyConfiguration, SubscriberConfiguration subscriberConfiguration) {
      return new BusDescriptorPublisher(busConfiguration, topologyConfiguration, subscriberConfiguration.getZooKeeper().getCurator());
   }
}
