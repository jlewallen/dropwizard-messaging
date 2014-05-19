package com.page5of4.dropwizard.activemq.example.publisher;

import com.page5of4.codon.config.InMemorySubscriptionStorageConfig;
import com.page5of4.codon.dropwizard.CodonBundle;
import com.page5of4.codon.dropwizard.NullTransactionConventionConfig;
import com.page5of4.codon.dropwizard.SimpleBusConfigurationConfig;
import com.page5of4.dropwizard.activemq.LocalActiveMqBundle;
import com.page5of4.dropwizard.discovery.zookeeper.ZooKeeperBundle;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class Main extends Application<PublisherConfiguration> {
   public static void main(String[] args) throws Exception {
      new Main().run(new String[] { "server", System.getProperty("dropwizard.config") == null ? "build/resources/main/activemq-publisher.yml" : System.getProperty("dropwizard.config") });
   }

   @Override
   public void initialize(Bootstrap<PublisherConfiguration> bootstrap) {
      bootstrap.addBundle(new LocalActiveMqBundle());
      bootstrap.addBundle(new CodonBundle(SimpleBusConfigurationConfig.class, InMemorySubscriptionStorageConfig.class, NullTransactionConventionConfig.class));
      bootstrap.addBundle(new ZooKeeperBundle(false, "127.0.0.1:2181"));
   }

   @Override
   public void run(PublisherConfiguration configuration, Environment environment) throws ClassNotFoundException {
      environment.jersey().register(DummyResource.class);
   }
}
