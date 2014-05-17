package com.page5of4.codon.spring;

import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class ContextFactorySpecs {
   @Test
   public void when_creating_context() {
      AbstractApplicationContext applicationContext = new GenericXmlApplicationContext("classpath:spring/test-context.xml");
      applicationContext.destroy();
   }
}
