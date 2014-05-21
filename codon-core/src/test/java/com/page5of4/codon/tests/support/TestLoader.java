package com.page5of4.codon.tests.support;

import com.page5of4.codon.BusModule;
import com.page5of4.codon.config.PublisherConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextLoader;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

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
      applicationContext.register(StartAndStopBusWithContextConfig.class);
      applicationContext.register(TestHandlersConfig.class);
      applicationContext.refresh();
      applicationContext.registerShutdownHook();
      return applicationContext;
   }

   @Configuration
   public static class StartAndStopBusWithContextConfig {
      @Bean
      public StartAndStopBusWithContext startAndStopBusWithContext(BusModule busModule) {
         return new StartAndStopBusWithContext(busModule);
      }
   }

   public static class StartAndStopBusWithContext  {
      private final BusModule busModule;

      public StartAndStopBusWithContext(BusModule busModule) {
         this.busModule = busModule;
      }

      @PostConstruct
      public void postConstruct() {
         busModule.start();
      }

      @PreDestroy
      public void preDestroy() {
         busModule.stop();
      }
   }
}
