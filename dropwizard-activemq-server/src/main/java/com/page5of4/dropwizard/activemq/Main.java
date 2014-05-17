package com.page5of4.dropwizard.activemq;

import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class Main extends Application<Configuration> {
   public static void main(String[] args) throws Exception {
      new Main().run(new String[] { "server", System.getProperty("dropwizard.config") });
   }

   @Override
   public void initialize(Bootstrap<Configuration> bootstrap) {
   }

   @Override
   public void run(Configuration configuration, Environment environment) throws ClassNotFoundException {
   }
}
