package com.page5of4.dropwizard.activemq.example.publisher;

import com.page5of4.dropwizard.activemq.LocalActiveMqBundle;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class Main extends Application<PublisherConfiguration> {
    public static void main(String[] args) throws Exception {
        new Main().run(new String[]{"server", System.getProperty("dropwizard.config")});
    }

    @Override
    public void initialize(Bootstrap<PublisherConfiguration> bootstrap) {
        bootstrap.addBundle(new LocalActiveMqBundle());
    }

    @Override
    public void run(PublisherConfiguration configuration, Environment environment) throws ClassNotFoundException {
        environment.jersey().register(DummyResource.class);
    }

}
