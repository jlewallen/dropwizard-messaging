package com.page5of4.codon.config;

import com.page5of4.codon.BusException;
import org.apache.camel.CamelContext;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.spi.PackageScanFilter;
import org.apache.camel.spring.ContextScanRouteBuilderFinder;
import org.apache.camel.spring.SpringCamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class CamelContextConfig {
   @Autowired
   private ApplicationContext applicationContext;

   @Bean(initMethod = "start", destroyMethod = "stop")
   public CamelContext camelContext() {
      try {
         SpringCamelContext camelContext = new SpringCamelContext(applicationContext);
         PackageScanFilter filter = new PackageScanFilter() {
            @Override
            public boolean matches(Class<?> type) {
               return true;
            }
         };
         ContextScanRouteBuilderFinder finder = new ContextScanRouteBuilderFinder(camelContext, filter);
         List<RoutesBuilder> builders = new ArrayList<RoutesBuilder>();
         finder.appendBuilders(builders);
         for(RoutesBuilder builder : builders) {
            camelContext.addRoutes(builder);
         }
         camelContext.afterPropertiesSet();
         return camelContext;
      }
      catch(Exception e) {
         throw new BusException(e);
      }
   }
}
