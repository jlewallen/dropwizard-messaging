package com.page5of4.codon.tests.support;

import org.apache.camel.model.ModelCamelContext;
import org.apache.camel.model.RouteDefinition;

public abstract class RouteUtils {

    public static RouteDefinition find(ModelCamelContext camelContext, String pattern) {
        for (RouteDefinition definition : camelContext.getRouteDefinitions()) {
            if (definition.getId().matches(pattern)) {
                return definition;
            }
        }
        throw new RuntimeException();
    }

}
