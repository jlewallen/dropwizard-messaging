package com.page5of4.codon.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

public class ApplicationContextResolver implements InstanceResolver {
   private static final Logger logger = LoggerFactory.getLogger(ApplicationContextResolver.class);
   private final ApplicationContext applicationContext;

   public ApplicationContextResolver(ApplicationContext applicationContext) {
      super();
      this.applicationContext = applicationContext;
   }

   @Override
   public Object resolve(Class<?> type) {
      logger.debug("Resolving {} from {}", type, applicationContext);
      return applicationContext.getBean(type);
   }
}
