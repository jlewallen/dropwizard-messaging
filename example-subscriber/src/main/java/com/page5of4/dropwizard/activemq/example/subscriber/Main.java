package com.page5of4.dropwizard.activemq.example.subscriber;

import com.page5of4.codon.Bus;
import com.page5of4.codon.BusConfiguration;
import com.page5of4.codon.PropertiesConfiguration;
import com.page5of4.codon.config.BusConfig;
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
      new Main().run(new String[] { "server", System.getProperty("dropwizard.config") == null ? "build/resources/main/activemq-subscriber.yml" : System.getProperty("dropwizard.config") });
   }

   @Override
   public void initialize(Bootstrap<SubscriberConfiguration> bootstrap) {
      bootstrap.addBundle(new ZooKeeperBundle(false));
      bootstrap.addBundle(new LocalActiveMqBundle());
      bootstrap.addBundle(new CodonBundle(CodonConfig.class));
   }

   @Override
   public void run(final SubscriberConfiguration configuration, final Environment environment) throws ClassNotFoundException {
      environment.jersey().register(new DummyResource());
   }

   @Configuration
   public static class CodonConfig extends BusConfig {
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
         // configuration.put("bus.owner.com.page5of4.dropwizard", "remote:remote.{messageType}");
         return configuration;
      }

      @Bean
      public BusDescriptorPublisher busDescriptorPublisher(BusConfiguration busConfiguration, TopologyConfiguration topologyConfiguration, SubscriberConfiguration subscriberConfiguration) {
         return new BusDescriptorPublisher(busConfiguration, topologyConfiguration, subscriberConfiguration.getZooKeeperConfiguration().getCurator());
      }
   }
}
