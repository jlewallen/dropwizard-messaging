package com.page5of4.dropwizard.activemq.example.subscriber;

import com.page5of4.codon.dropwizard.CodonBundle;
import com.page5of4.dropwizard.activemq.LocalActiveMqBundle;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class Main extends Application<SubscriberConfiguration> {
    public static void main(String[] args) throws Exception {
        new Main().run(new String[]{"server", System.getProperty("dropwizard.config")});
    }

    @Override
    public void initialize(Bootstrap<SubscriberConfiguration> bootstrap) {
        bootstrap.addBundle(new LocalActiveMqBundle());
        bootstrap.addBundle(new CodonBundle());
    }

    @Override
    public void run(SubscriberConfiguration configuration, Environment environment) throws ClassNotFoundException {
        environment.jersey().register(DummyResource.class);
    }
}
