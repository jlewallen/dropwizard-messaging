package com.page5of4.dropwizard.activemq.example.subscriber;

import com.page5of4.codon.Bus;
import com.page5of4.codon.BusConfiguration;
import com.page5of4.codon.PropertiesConfiguration;
import com.page5of4.codon.activmq.discovery.ActiveMqNetworkManager;
import com.page5of4.codon.config.BusConfig;
import com.page5of4.codon.config.PublisherConfig;
import com.page5of4.codon.discovery.BusDescriptorPublisher;
import com.page5of4.codon.dropwizard.CodonBundle;
import com.page5of4.codon.impl.TopologyConfiguration;
import com.page5of4.dropwizard.activemq.LocalActiveMqBundle;
import com.page5of4.dropwizard.discovery.LocalIpAddress;
import com.page5of4.dropwizard.discovery.zookeeper.ZooKeeperBundle;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class Main extends Application<SubscriberConfiguration> {
   private static final Logger logger = LoggerFactory.getLogger(Main.class);

   public static void main(String[] args) throws Exception {
      new Main().run(args);
   }

   @Override
   public void initialize(Bootstrap<SubscriberConfiguration> bootstrap) {
      bootstrap.addBundle(new ZooKeeperBundle(false));
      bootstrap.addBundle(new LocalActiveMqBundle());
      bootstrap.addBundle(new CodonBundle(NetworkedBrokersCodonConfig.class, PublisherConfig.class));
   }

   @Override
   public void run(final SubscriberConfiguration configuration, final Environment environment) throws ClassNotFoundException {
      environment.servlets().addServlet("jolokia", new org.jolokia.http.AgentServlet()).addMapping("/jolokia/*");
      environment.jersey().register(new DummyResource());
   }

   @Configuration
   public static class NetworkedBrokersCodonConfig extends BusConfig {
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
      public ActiveMqNetworkManager activeMqNetworkManager(BusConfiguration busConfiguration, TopologyConfiguration topologyConfiguration, SubscriberConfiguration publisherConfiguration) {
         return new ActiveMqNetworkManager(busConfiguration, topologyConfiguration, publisherConfiguration.getZooKeeperConfiguration().getCurator(), publisherConfiguration.getBrokerConfiguration().createBroker());
      }
   }

   @Configuration
   public static class NonNewtorkedBrokersCodonConfig extends BusConfig {
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
         return configuration;
      }

      @Bean
      public BusDescriptorPublisher busDescriptorPublisher(BusConfiguration busConfiguration, TopologyConfiguration topologyConfiguration, SubscriberConfiguration subscriberConfiguration) {
         return new BusDescriptorPublisher(busConfiguration, topologyConfiguration, subscriberConfiguration.getZooKeeperConfiguration().getCurator());
      }
   }
}
