package com.page5of4.codon.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public class AnnotationConfiguredCodonBeanDefinitionParser implements BeanDefinitionParser {
   private static final Logger logger = LoggerFactory.getLogger(AnnotationConfiguredCodonBeanDefinitionParser.class);

   @Override
   public BeanDefinition parse(Element element, ParserContext parserContext) {
      logger.info("Configuring Codon");
      // registerAutowiredConstructor(parserContext, "muleContext", MuleContextFactory.class);
      return null;
   }
}
