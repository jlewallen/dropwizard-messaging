package com.page5of4.dropwizard.activemq.example.publisher;

import com.page5of4.codon.BusConfiguration;
import com.page5of4.codon.CommunicationConfiguration;
import com.page5of4.codon.PropertiesConfiguration;
import com.page5of4.codon.activmq.discovery.ActiveMqNetworkManager;
import com.page5of4.codon.config.BusConfig;
import com.page5of4.codon.config.PublisherConfig;
import com.page5of4.codon.discovery.BusDescriptorPublisher;
import com.page5of4.codon.discovery.ServiceRegistryCommunicationConfiguration;
import com.page5of4.codon.discovery.ServiceRegistrySubscriptionStorage;
import com.page5of4.codon.dropwizard.CodonBundle;
import com.page5of4.codon.impl.TopologyConfiguration;
import com.page5of4.codon.subscriptions.SubscriptionStorage;
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

public class Main extends Application<PublisherConfiguration> {
   private static final Logger logger = LoggerFactory.getLogger(Main.class);

   public static void main(String[] args) throws Exception {
      new Main().run(args);
   }

   @Override
   public void initialize(Bootstrap<PublisherConfiguration> bootstrap) {
      bootstrap.addBundle(new ZooKeeperBundle(false));
      bootstrap.addBundle(new LocalActiveMqBundle());
      bootstrap.addBundle(new CodonBundle(NetworkedBrokersCodonConfig.class, PublisherConfig.class));
   }

   @Override
   public void run(final PublisherConfiguration configuration, final Environment environment) throws ClassNotFoundException {
      environment.servlets().addServlet("jolokia", new org.jolokia.http.AgentServlet()).addMapping("/jolokia/*");
      environment.jersey().register(new DummyResource(configuration.getCodonConfiguration().getBus()));
   }

   @Configuration
   public static class NetworkedBrokersCodonConfig extends BusConfig {
      @Autowired
      private PublisherConfiguration publisherConfiguration;

      @Bean
      @Override
      public BusConfiguration busConfiguration() {
         Integer port = publisherConfiguration.getBrokerConfiguration().getPort();
         String localBrokerUrl = "tcp://" + LocalIpAddress.guessLocalIp().getHostAddress() + ":" + port;
         return new PropertiesConfiguration("publisher", localBrokerUrl);
      }

      @Bean
      public ActiveMqNetworkManager activeMqNetworkManager(BusConfiguration busConfiguration, TopologyConfiguration topologyConfiguration, PublisherConfiguration publisherConfiguration) {
         return new ActiveMqNetworkManager(busConfiguration, topologyConfiguration, publisherConfiguration.getZooKeeperConfiguration().getCurator(), publisherConfiguration.getBrokerConfiguration().createBroker());
      }
   }

   @Configuration
   public static class NonNetworkedBrokersCodonConfig extends BusConfig {
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
         return new BusDescriptorPublisher(busConfiguration, topologyConfiguration, publisherConfiguration.getZooKeeperConfiguration().getCurator());
      }

      @Override
      public SubscriptionStorage subscriptionStorage() {
         return new ServiceRegistrySubscriptionStorage();
      }
   }
}
