package com.page5of4.codon.impl;

public class HandlerInspector {
   private final InstanceResolver resolver;

   public HandlerInspector(InstanceResolver resolver) {
      super();
      this.resolver = resolver;
   }

   public HandlerDescriptor discoverBindings(final Class<?> klass) {
      return new HandlerDescriptor(klass, resolver);
   }
}
