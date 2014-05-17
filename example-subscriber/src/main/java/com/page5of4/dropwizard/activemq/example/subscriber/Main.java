package com.page5of4.dropwizard.activemq.example.subscriber;

import com.page5of4.codon.dropwizard.CodonBundle;
import com.page5of4.dropwizard.activemq.LocalActiveMqBundle;
import com.page5of4.dropwizard.discovery.zookeeper.ServiceInstanceRecord;
import com.page5of4.dropwizard.discovery.zookeeper.ServiceRegistry;
import com.page5of4.dropwizard.discovery.zookeeper.ZooKeeperBundle;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class Main extends Application<SubscriberConfiguration> {
   public static void main(String[] args) throws Exception {
      new Main().run(new String[] { "server", System.getProperty("dropwizard.config") });
   }

   @Override
   public void initialize(Bootstrap<SubscriberConfiguration> bootstrap) {
      bootstrap.addBundle(new LocalActiveMqBundle());
      bootstrap.addBundle(new CodonBundle());
      bootstrap.addBundle(new ZooKeeperBundle(false, "127.0.0.1:2181"));
   }

   @Override
   public void run(SubscriberConfiguration configuration, Environment environment) throws ClassNotFoundException {
      ServiceRegistry.get().publish(new ServiceInstanceRecord("SUB"));

      environment.jersey().register(DummyResource.class);
   }
}
