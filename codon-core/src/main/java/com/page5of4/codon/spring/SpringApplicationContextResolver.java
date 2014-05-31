package com.page5of4.codon.spring;

import com.page5of4.codon.impl.InstanceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

public class SpringApplicationContextResolver implements InstanceResolver {
   private static final Logger logger = LoggerFactory.getLogger(SpringApplicationContextResolver.class);
   private final ApplicationContext applicationContext;

   public SpringApplicationContextResolver(ApplicationContext applicationContext) {
      super();
      this.applicationContext = applicationContext;
   }

   @Override
   public Object resolve(Class<?> type) {
      logger.debug("Resolving {} from {}", type, applicationContext);
      return applicationContext.getBean(type);
   }
}
