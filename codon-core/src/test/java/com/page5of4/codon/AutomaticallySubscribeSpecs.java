package com.page5of4.codon;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class AutomaticallySubscribeSpecs {
   @Test
   public void default_or_default_should_always_subscribe() {
      assertThat(AutomaticallySubscribe.DEFAULT.or(AutomaticallySubscribe.DEFAULT).shouldSubscribe()).isTrue();
   }

   @Test
   public void default_or_never_should_never_subscribe() {
      assertThat(AutomaticallySubscribe.DEFAULT.or(AutomaticallySubscribe.NEVER).shouldSubscribe()).isFalse();
   }

   @Test
   public void default_or_always_should_always_subscribe() {
      assertThat(AutomaticallySubscribe.DEFAULT.or(AutomaticallySubscribe.ALWAYS).shouldSubscribe()).isTrue();
   }

   @Test
   public void never_or_always_should_always_subscribe() {
      assertThat(AutomaticallySubscribe.NEVER.or(AutomaticallySubscribe.ALWAYS).shouldSubscribe()).isTrue();
   }

   @Test
   public void never_or_never_should_never_subscribe() {
      assertThat(AutomaticallySubscribe.NEVER.or(AutomaticallySubscribe.NEVER).shouldSubscribe()).isFalse();
   }

   @Test
   public void never_or_default_should_never_subscribe() {
      assertThat(AutomaticallySubscribe.NEVER.or(AutomaticallySubscribe.DEFAULT).shouldSubscribe()).isFalse();
   }
}
