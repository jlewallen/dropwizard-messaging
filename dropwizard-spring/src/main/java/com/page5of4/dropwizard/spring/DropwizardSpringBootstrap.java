package com.page5of4.dropwizard.spring;

import io.dropwizard.Configuration;
import io.dropwizard.setup.Environment;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class DropwizardSpringBootstrap<T extends Configuration> {
   private static final String ENVIRONMENT_BEAN_NAME = "dropwizardEnvironment";
   private static final String CONFIGURATION_BEAN_NAME = "dropwizardConfiguration";
   private Environment environment;
   private T configuration;

   public DropwizardSpringBootstrap(T configuration, Environment environment) {
      this.environment = environment;
      this.configuration = configuration;
   }

   public ApplicationContext createContext(Class<?>... configurationClasses) {
      return createContext(null, configurationClasses);
   }

   public ApplicationContext createContext(ApplicationContext parentContext, Class<?>... configurationClasses) {
      AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
      for(Class<?> configurationClass : configurationClasses) {
         applicationContext.register(configurationClass);
      }
      if(parentContext != null) {
         applicationContext.setParent(parentContext);
      }
      applicationContext.getBeanFactory().registerSingleton(ENVIRONMENT_BEAN_NAME, environment);
      applicationContext.getBeanFactory().registerSingleton(CONFIGURATION_BEAN_NAME, configuration);
      applicationContext.refresh();
      return applicationContext;
   }
}
