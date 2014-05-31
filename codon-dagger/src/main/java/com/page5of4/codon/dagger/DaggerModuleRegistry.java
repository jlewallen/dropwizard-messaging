package com.page5of4.codon.dagger;

import com.page5of4.codon.HandlerBinding;
import com.page5of4.codon.HandlerRegistry;

import java.util.List;

public class DaggerModuleRegistry implements HandlerRegistry {
   @Override
   public void initialize() {

   }

   @Override
   public void addAll(List<Class<?>> classes) {

   }

   @Override
   public List<HandlerBinding> getBindings() {
      return null;
   }

   @Override
   public List<HandlerBinding> getBindingsFor(Class<? extends Object> messageType) {
      return null;
   }
}
