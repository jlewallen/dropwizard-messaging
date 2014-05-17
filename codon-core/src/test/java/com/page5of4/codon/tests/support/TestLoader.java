package com.page5of4.codon.tests.support;

import com.page5of4.codon.config.InMemorySubscriptionStorageConfig;
import com.page5of4.codon.config.JmsTransactionConventionConfig;
import com.page5of4.codon.config.PublisherConfig;
import com.page5of4.codon.config.StandaloneConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextLoader;

/**
 * When we can use Spring 3.1.0, we should get rid of this.
 *
 * @author jlewallen
 */
public class TestLoader implements ContextLoader {
    @Override
    public String[] processLocations(Class<?> clazz, String... locations) {
        return locations;
    }

    @Override
    public ApplicationContext loadContext(String... locations) throws Exception {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(EmbeddedActiveMqBrokerConfig.class);
        applicationContext.register(SimpleBusConfigurationConfig.class);
        applicationContext.register(StandaloneConfig.class);
        applicationContext.register(PublisherConfig.class);
        applicationContext.register(ExposeCamelContextConfig.class);
        applicationContext.register(InMemorySubscriptionStorageConfig.class);
        applicationContext.register(JmsTransactionConventionConfig.class);
        applicationContext.register(TestHandlersConfig.class);
        applicationContext.refresh();
        applicationContext.registerShutdownHook();
        return applicationContext;
    }
}
