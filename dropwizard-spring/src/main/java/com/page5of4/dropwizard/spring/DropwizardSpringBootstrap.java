package com.page5of4.dropwizard.spring;

import io.dropwizard.setup.Environment;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.security.auth.login.Configuration;

public class DropwizardSpringBootstrap<T extends Configuration> {
   private static final String ENVIRONMENT_BEAN_NAME = "dropwizardEnvironment";
   private static final String CONFIGURATION_BEAN_NAME = "dropwizardConfiguration";
   private Environment environment;
   private T configuration;

   public DropwizardSpringBootstrap(Environment environment, T configuration) {
      this.environment = environment;
      this.configuration = configuration;
   }

   public ApplicationContext createContext(Class<?>... configurationClasses) {
      AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
      for(Class<?> configurationClass : configurationClasses) {
         applicationContext.register(configurationClass);
      }
      applicationContext.getBeanFactory().registerSingleton(ENVIRONMENT_BEAN_NAME, environment);
      applicationContext.getBeanFactory().registerSingleton(CONFIGURATION_BEAN_NAME, configuration);
      applicationContext.refresh();
      return applicationContext;
   }
}
