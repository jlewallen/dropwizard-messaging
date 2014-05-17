package com.page5of4.dropwizard.activemq.example.subscriber;

import com.page5of4.dropwizard.activemq.LocalActiveMqBundle;
import io.dropwizard.Application;
import io.dropwizard.lifecycle.Managed;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.apache.camel.impl.DefaultCamelContext;

public class Main extends Application<SubscriberConfiguration> {
    public static void main(String[] args) throws Exception {
        new Main().run(new String[]{"server", System.getProperty("dropwizard.config")});
    }

    @Override
    public void initialize(Bootstrap<SubscriberConfiguration> bootstrap) {
        bootstrap.addBundle(new LocalActiveMqBundle());
    }

    @Override
    public void run(SubscriberConfiguration configuration, Environment environment) throws ClassNotFoundException {
        environment.jersey().register(DummyResource.class);
        environment.lifecycle().manage(new Testing());
    }

    public static class Testing implements Managed {
        private DefaultCamelContext camelContext;

        @Override
        public void start() throws Exception {
            camelContext = new DefaultCamelContext();
            camelContext.start();
        }

        @Override
        public void stop() throws Exception {
            camelContext.stop();
        }
    }
}
