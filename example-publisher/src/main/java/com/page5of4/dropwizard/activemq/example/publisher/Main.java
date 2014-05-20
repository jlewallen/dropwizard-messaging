package com.page5of4.dropwizard.activemq.example.publisher;

import com.page5of4.codon.BusConfiguration;
import com.page5of4.codon.PropertiesConfiguration;
import com.page5of4.codon.config.InMemorySubscriptionStorageConfig;
import com.page5of4.codon.discovery.BusDescriptorPublisher;
import com.page5of4.codon.dropwizard.CodonBundle;
import com.page5of4.codon.dropwizard.NullTransactionConventionConfig;
import com.page5of4.codon.impl.TopologyConfiguration;
import com.page5of4.dropwizard.activemq.LocalActiveMqBundle;
import com.page5of4.dropwizard.discovery.LocalIpAddress;
import com.page5of4.dropwizard.discovery.zookeeper.ZooKeeperBundle;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class Main extends Application<PublisherConfiguration> {
   private static final Logger logger = LoggerFactory.getLogger(Main.class);

   public static void main(String[] args) throws Exception {
      new Main().run(new String[] { "server", System.getProperty("dropwizard.config") == null ? "build/resources/main/activemq-publisher.yml" : System.getProperty("dropwizard.config") });
   }

   @Override
   public void initialize(Bootstrap<PublisherConfiguration> bootstrap) {
      bootstrap.addBundle(new ZooKeeperBundle(false));
      bootstrap.addBundle(new LocalActiveMqBundle());
      bootstrap.addBundle(new CodonBundle(InMemorySubscriptionStorageConfig.class, NullTransactionConventionConfig.class, CodonConfiguration.class));
   }

   @Override
   public void run(PublisherConfiguration configuration, Environment environment) throws ClassNotFoundException {
      logger.info("Run");
      environment.jersey().register(DummyResource.class);
   }

   @Configuration
   public static class CodonConfiguration {
      @Bean
      public BusConfiguration busConfiguration(PublisherConfiguration publisherConfiguration) {
         Integer port = publisherConfiguration.getBrokerConfiguration().getPort();
         String localBrokerUrl = "tcp://" + LocalIpAddress.guessLocalIp().getHostAddress() + ":" + port;
         PropertiesConfiguration configuration = new PropertiesConfiguration("publisher", localBrokerUrl);
         configuration.put("bus.owner.com.page5of4.dropwizard", "remote:remote.{messageType}");
         return configuration;
      }

      @Bean
      public BusDescriptorPublisher busDescriptorPublisher(BusConfiguration busConfiguration, TopologyConfiguration topologyConfiguration) {
         return new BusDescriptorPublisher(busConfiguration, topologyConfiguration);
      }
   }
}
