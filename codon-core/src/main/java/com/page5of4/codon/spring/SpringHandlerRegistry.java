package com.page5of4.codon.spring;

import com.page5of4.codon.MessageHandler;
import com.page5of4.codon.impl.AbstractHandlerRegistry;
import com.page5of4.codon.impl.InstanceResolver;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SpringHandlerRegistry extends AbstractHandlerRegistry {
   private final ApplicationContext applicationContext;

   public SpringHandlerRegistry(InstanceResolver resolver, ApplicationContext applicationContext) {
      super(resolver);
      this.applicationContext = applicationContext;
   }

   @Override
   public List<Class<?>> getHandlerClasses() {
      List<Class<?>> classes = new ArrayList<Class<?>>();
      Map<String, Object> handlers = applicationContext.getBeansWithAnnotation(MessageHandler.class);
      for(Map.Entry<String, Object> entry : handlers.entrySet()) {
         classes.add(entry.getValue().getClass());
      }
      return classes;
   }
}
