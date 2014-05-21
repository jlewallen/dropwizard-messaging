package com.page5of4.codon.config;

import com.page5of4.codon.Bus;
import com.page5of4.codon.BusConfiguration;
import com.page5of4.codon.BusEvents;
import com.page5of4.codon.BusException;
import com.page5of4.codon.BusModule;
import com.page5of4.codon.HandlerRegistry;
import com.page5of4.codon.Transport;
import com.page5of4.codon.camel.CodonComponentResolver;
import com.page5of4.codon.camel.DefaultCamelTransport;
import com.page5of4.codon.camel.InvokeHandlerProcessor;
import com.page5of4.codon.impl.BusConfigurationTopologyConfiguration;
import com.page5of4.codon.impl.BusContext;
import com.page5of4.codon.impl.BusContextProvider;
import com.page5of4.codon.impl.ConstantBusContextProvider;
import com.page5of4.codon.impl.DefaultBus;
import com.page5of4.codon.impl.NullTransactionManagerConvention;
import com.page5of4.codon.impl.SpringApplicationContextResolver;
import com.page5of4.codon.impl.SpringHandlerRegistry;
import com.page5of4.codon.impl.TopologyConfiguration;
import com.page5of4.codon.impl.TransactionConvention;
import com.page5of4.codon.subscriptions.SubscriptionStorage;
import com.page5of4.codon.subscriptions.impl.InMemorySubscriptionStorage;
import org.apache.camel.spring.SpringCamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;

@Configuration
public abstract class BusConfig {
   @Autowired
   private ApplicationContext applicationContext;

   @Bean
   public abstract BusConfiguration busConfiguration();

   @Bean
   public TransactionConvention transactionConvention() {
      return new NullTransactionManagerConvention();
   }

   @Bean
   public Bus bus(Collection<BusEvents> busEvents) {
      return new DefaultBus(busContextProvider(), transport(), busEvents);
   }

   @Bean
   public Transport transport() {
      try {
         SpringCamelContext camelContext = new SpringCamelContext(applicationContext);
         camelContext.setComponentResolver(new CodonComponentResolver(transactionConvention(), busConfiguration()));
         camelContext.afterPropertiesSet();
         return new DefaultCamelTransport(busConfiguration(), camelContext, invokeHandlerProcessor());
      }
      catch(Exception e) {
         throw new BusException(e);
      }
   }

   @Bean
   public InvokeHandlerProcessor invokeHandlerProcessor() {
      return new InvokeHandlerProcessor(handlerRegistry(), busContextProvider());
   }

   @Bean
   public HandlerRegistry handlerRegistry() {
      return new SpringHandlerRegistry(new SpringApplicationContextResolver(applicationContext), applicationContext);
   }

   @Bean
   public SubscriptionStorage subscriptionStorage() {
      return new InMemorySubscriptionStorage();
   }

   @Bean
   public BusContextProvider busContextProvider() {
      return new ConstantBusContextProvider(busContext());
   }

   @Bean
   public TopologyConfiguration topologyConfiguration() {
      return new BusConfigurationTopologyConfiguration(busConfiguration());
   }

   private BusContext busContext() {
      return new BusContext(topologyConfiguration(), subscriptionStorage());
   }

   /* I can't remember what this is for? -jlewallen
   @Bean
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
   */

   @Bean
   public BusModule busModule(Bus bus, Collection<BusEvents> busEventsCollection) {
      return new BusModule(handlerRegistry(), bus, transport(), busEventsCollection);
   }
}
