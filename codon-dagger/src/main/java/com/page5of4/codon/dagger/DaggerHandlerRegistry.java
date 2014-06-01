package com.page5of4.codon.dagger;

import com.page5of4.codon.impl.AbstractHandlerRegistry;
import com.page5of4.codon.impl.InstanceResolver;

import java.util.List;

public class DaggerHandlerRegistry extends AbstractHandlerRegistry {
   private StaticHandlerProvider handlerProvider;

   public DaggerHandlerRegistry(StaticHandlerProvider handlerProvider, InstanceResolver resolver) {
      super(resolver);
      this.handlerProvider = handlerProvider;
   }

   @Override
   public List<Class<?>> getHandlerClasses() {
      return handlerProvider.getHandlerClasses();
   }
}
