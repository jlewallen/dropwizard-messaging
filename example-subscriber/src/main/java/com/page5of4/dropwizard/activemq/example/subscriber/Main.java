package com.page5of4.dropwizard.activemq.example.subscriber;

import com.page5of4.codon.Bus;
import com.page5of4.codon.BusConfiguration;
import com.page5of4.codon.BusEvents;
import com.page5of4.codon.PropertiesConfiguration;
import com.page5of4.codon.config.InMemorySubscriptionStorageConfig;
import com.page5of4.codon.dropwizard.BusDescriptor;
import com.page5of4.codon.dropwizard.CodonBundle;
import com.page5of4.codon.dropwizard.NullTransactionConventionConfig;
import com.page5of4.codon.impl.TopologyConfiguration;
import com.page5of4.dropwizard.activemq.LocalActiveMqBundle;
import com.page5of4.dropwizard.discovery.zookeeper.ServiceRegistry;
import com.page5of4.dropwizard.discovery.zookeeper.ZooKeeperBundle;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
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
      bootstrap.addBundle(new ZooKeeperBundle(false, "127.0.0.1:2181"));
      bootstrap.addBundle(new LocalActiveMqBundle());
      bootstrap.addBundle(new CodonBundle(InMemorySubscriptionStorageConfig.class, NullTransactionConventionConfig.class, CodonConfiguration.class));
   }

   @Override
   public void run(SubscriberConfiguration configuration, Environment environment) throws ClassNotFoundException {
      logger.info("Run");
      environment.jersey().register(DummyResource.class);
   }

   @Configuration
   public static class CodonConfiguration {
      @Bean
      public LaunchWorkHandler launchWorkHandler(Bus bus) {
         return new LaunchWorkHandler(bus);
      }

      @Bean
      public BusConfiguration busConfiguration(SubscriberConfiguration subscriberConfiguration) {
         PropertiesConfiguration configuration = new PropertiesConfiguration("test", "activemq");
         configuration.setLocalUrl("tcp://127.0.0.1:" + subscriberConfiguration.getBrokerConfiguration().getPort());
         configuration.put("bus.owner.com.page5of4.dropwizard", "remote:remote.{messageType}");
         return configuration;
      }

      @Bean
      public RegistrationManager registrationManager(BusConfiguration busConfiguration, TopologyConfiguration topologyConfiguration) {
         return new RegistrationManager(busConfiguration, topologyConfiguration);
      }
   }

   public static class RegistrationManager implements BusEvents {
      private static final Logger logger = LoggerFactory.getLogger(RegistrationManager.class);
      private final BusDescriptor descriptor = new BusDescriptor();
      private final BusConfiguration busConfiguration;
      private TopologyConfiguration topologyConfiguration;

      public RegistrationManager(BusConfiguration busConfiguration, TopologyConfiguration topologyConfiguration) {
         this.busConfiguration = busConfiguration;
         this.topologyConfiguration = topologyConfiguration;
      }

      @Override
      public void started() {
         logger.info("Publishing BusDescriptor...");
         ServiceRegistry.get().publish(descriptor);
      }

      @Override
      public void subscribe(Class<?> messageType) {
      }

      @Override
      public void unsubscribe(Class<?> messageType) {
         throw new RuntimeException("Not supported");
      }

      @Override
      public void listen(Class<?> messageType) {
         descriptor.addListener(messageType.getName(), topologyConfiguration.getLocalAddressOf(messageType).toString());
      }

      @Override
      public void unlisten(Class<?> messageType) {
         throw new RuntimeException("Not supported");
      }

      @Override
      public void stopped() {
         ServiceRegistry.get().unpublish(descriptor);
      }
   }
}
