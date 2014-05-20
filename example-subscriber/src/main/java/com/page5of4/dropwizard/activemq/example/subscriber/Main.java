package com.page5of4.dropwizard.activemq.example.subscriber;

import com.page5of4.codon.Bus;
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
import io.dropwizard.lifecycle.Managed;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
      bootstrap.addBundle(new CodonBundle(InMemorySubscriptionStorageConfig.class, NullTransactionConventionConfig.class, CodonConfiguration.class));
   }

   @Override
   public void run(final SubscriberConfiguration configuration, Environment environment) throws ClassNotFoundException {
      environment.jersey().register(DummyResource.class);
      environment.lifecycle().manage(new Managed() {
         @Override
         public void start() throws Exception {
            configuration.getZooKeeperConfiguration().getCurator().create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath("/services/whatever");
         }

         @Override
         public void stop() throws Exception {

         }
      });
   }

   @Configuration
   public static class CodonConfiguration {
      @Bean
      public LaunchWorkHandler launchWorkHandler(Bus bus) {
         return new LaunchWorkHandler(bus);
      }

      @Bean
      public BusConfiguration busConfiguration(SubscriberConfiguration subscriberConfiguration) {
         Integer port = subscriberConfiguration.getBrokerConfiguration().getPort();
         String localBrokerUrl = "tcp://" + LocalIpAddress.guessLocalIp().getHostAddress() + ":" + port;
         PropertiesConfiguration configuration = new PropertiesConfiguration("subscriber", localBrokerUrl);
         configuration.put("bus.owner.com.page5of4.dropwizard", "remote:remote.{messageType}");
         return configuration;
      }

      @Bean
      public BusDescriptorPublisher busDescriptorPublisher(BusConfiguration busConfiguration, TopologyConfiguration topologyConfiguration) {
         return new BusDescriptorPublisher(busConfiguration, topologyConfiguration);
      }
   }
}
