package com.page5of4.codon.tests.support;

import com.page5of4.codon.config.PublisherConfig;
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
      applicationContext.register(PublisherConfig.class);
      applicationContext.register(ExposeCamelContextConfig.class);
      applicationContext.register(TestHandlersConfig.class);
      applicationContext.refresh();
      applicationContext.registerShutdownHook();
      return applicationContext;
   }
}
