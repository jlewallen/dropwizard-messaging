package com.page5of4.codon;

import com.page5of4.codon.spring.SpringHandlerRegistry;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class HandlerRegistrySpecs {
   private HandlerRegistry registry;

   @Before
   public void before() {
      registry = new SpringHandlerRegistry(null, null);
      List<Class<?>> classes = new ArrayList<Class<?>>();
      classes.add(OneHandler.class);
      classes.add(TwoHandlers.class);
      registry.addAll(classes);
   }

   @Test
   public void when_finding_bindings_for_message_with_no_bindings_should_return_no_bindings() {
      List<HandlerBinding> bindings = registry.getBindingsFor(Long.class);
      assertThat(bindings).isEmpty();
   }

   @Test
   public void when_message_has_one_binding() {
      List<HandlerBinding> bindings = registry.getBindingsFor(MessageB.class);
      assertThat(bindings.size()).isEqualTo(1);
      assertThat(bindings.get(0).getHandlerType()).isEqualTo(OneHandler.class);
      assertThat(bindings.get(0).getMessageType()).isEqualTo(MessageB.class);
      assertThat(bindings.get(0).getMethod().getName()).isEqualTo("handle");
   }

   @Test
   public void when_message_has_one_binding_on_a_class_with_two() {
      List<HandlerBinding> bindings = registry.getBindingsFor(MessageA.class);
      assertThat(bindings.size()).isEqualTo(1);
      assertThat(bindings.get(0).getHandlerType()).isEqualTo(TwoHandlers.class);
      assertThat(bindings.get(0).getMessageType()).isEqualTo(MessageA.class);
      assertThat(bindings.get(0).getMethod().getName()).isEqualTo("first");
   }

   @Test
   public void when_message_has_two_bindings() {
      List<HandlerBinding> bindings = registry.getBindingsFor(MessageCExtendsB.class);
      assertThat(bindings.size()).isEqualTo(2);
      assertThat(bindings.get(0).getHandlerType()).isEqualTo(OneHandler.class);
      assertThat(bindings.get(0).getMessageType()).isEqualTo(MessageB.class);
      assertThat(bindings.get(0).getMethod().getName()).isEqualTo("handle");
      assertThat(bindings.get(1).getHandlerType()).isEqualTo(TwoHandlers.class);
      assertThat(bindings.get(1).getMessageType()).isEqualTo(MessageCExtendsB.class);
      assertThat(bindings.get(1).getMethod().getName()).isEqualTo("second");
   }

   @MessageHandler
   public static class OneHandler {
      @MessageHandler
      public void handle(MessageB message) {
      }
   }

   @MessageHandler
   public static class TwoHandlers {
      @MessageHandler
      public void first(MessageA message) {
      }

      @MessageHandler
      public void second(MessageCExtendsB message) {
      }
   }

   public static class MessageA {
   }

   public static class MessageB {
   }

   public static class MessageCExtendsB extends MessageB {
   }
}
