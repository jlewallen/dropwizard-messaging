package com.page5of4.codon.dagger;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.page5of4.codon.BusException;
import com.page5of4.codon.impl.InstanceResolver;
import dagger.Lazy;

import java.util.List;
import java.util.Map;

public class StaticHandlerProvider implements InstanceResolver {
   private final Map<Class<?>, Lazy<?>> handlers = Maps.newHashMap();

   public void registerHandler(Class<?> handler, Lazy<?> resolver) {
      handlers.put(handler, resolver);
   }

   public List<Class<?>> getHandlerClasses() {
      return Lists.newArrayList(handlers.keySet());
   }

   @Override
   public Object resolve(Class<?> klass) {
      if(!handlers.containsKey(klass)) {
         throw new BusException("No such handler registered with StaticHandlerProvider: " + klass);
      }
      return handlers.get(klass).get();
   }
}
