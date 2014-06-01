package com.page5of4.dropwizard.activemq.example.publisher;

import com.page5of4.codon.dropwizard.CodonBundle;
import com.page5of4.codon.spring.config.PublisherConfig;
import com.page5of4.dropwizard.discovery.zookeeper.ZooKeeperBundle;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

public class Main extends Application<PublisherConfiguration> {
   private static final Logger logger = LoggerFactory.getLogger(Main.class);

   public static void main(String[] args) throws Exception {
      new Main().run(args);
   }

   @Override
   public void initialize(Bootstrap<PublisherConfiguration> bootstrap) {
      bootstrap.addBundle(new ZooKeeperBundle());
      bootstrap.addBundle(new CodonBundle(NetworkedBrokersCodonConfig.class, PublisherConfig.class));
   }

   @Override
   public void run(final PublisherConfiguration configuration, final Environment environment) throws ClassNotFoundException {
      ApplicationContext applicationContext = configuration.getCodonConfiguration().getApplicationContext();
      new JolokiaInstaller().install(environment);
      environment.jersey().register(applicationContext.getBean(DummyResource.class));
   }
}
