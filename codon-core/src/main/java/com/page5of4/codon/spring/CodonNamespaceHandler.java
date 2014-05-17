package com.page5of4.codon.spring;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class CodonNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("annotation-driven", new AnnotationConfiguredCodonBeanDefinitionParser());
    }
}
