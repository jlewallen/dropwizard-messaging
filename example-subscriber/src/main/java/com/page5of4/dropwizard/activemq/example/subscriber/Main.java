package com.page5of4.dropwizard.activemq.example.subscriber;

import com.page5of4.codon.config.PublisherConfig;
import com.page5of4.codon.dropwizard.CodonBundle;
import com.page5of4.dropwizard.activemq.LocalActiveMqBundle;
import com.page5of4.dropwizard.activemq.example.publisher.JolokiaInstaller;
import com.page5of4.dropwizard.discovery.zookeeper.ZooKeeperBundle;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main extends Application<SubscriberConfiguration> {
   private static final Logger logger = LoggerFactory.getLogger(Main.class);

   public static void main(String[] args) throws Exception {
      new Main().run(args);
   }

   @Override
   public void initialize(Bootstrap<SubscriberConfiguration> bootstrap) {
      bootstrap.addBundle(new ZooKeeperBundle());
      bootstrap.addBundle(new LocalActiveMqBundle());
      bootstrap.addBundle(new CodonBundle(NetworkedBrokersCodonConfig.class, PublisherConfig.class));
   }

   @Override
   public void run(final SubscriberConfiguration configuration, final Environment environment) throws ClassNotFoundException {
      new JolokiaInstaller().install(environment);
      environment.jersey().register(new DummyResource());
   }
}
