package com.page5of4.codon;

import com.page5of4.codon.camel.CodonComponentResolver;
import com.page5of4.codon.camel.DefaultCamelTransport;
import com.page5of4.codon.camel.InvokeHandlerProcessor;
import com.page5of4.codon.impl.ApplicationContextResolver;
import com.page5of4.codon.impl.BusContext;
import com.page5of4.codon.impl.BusContextProvider;
import com.page5of4.codon.impl.ConstantBusContextProvider;
import com.page5of4.codon.impl.DefaultBus;
import com.page5of4.codon.impl.InstanceResolver;
import com.page5of4.codon.impl.NullTransactionManagerConvention;
import com.page5of4.codon.impl.SpringHandlerRegistry;
import com.page5of4.codon.impl.TopologyConfiguration;
import com.page5of4.codon.impl.TransactionConvention;
import com.page5of4.codon.subscriptions.SubscriptionStorage;
import com.page5of4.codon.subscriptions.impl.InMemorySubscriptionStorage;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.ModelCamelContext;
import org.apache.camel.spring.SpringCamelContext;
import org.springframework.context.ApplicationContext;

public class BusBuilder {
   private final ModelCamelContext camelContext = new DefaultCamelContext();
   private final InstanceResolver resolver = new ApplicationContextResolver(null);
   private final HandlerRegistry handlerRegistry = new SpringHandlerRegistry(null, resolver);
   private SubscriptionStorage subscriptionStorage = new InMemorySubscriptionStorage();
   private TransactionConvention transactionConvention = new NullTransactionManagerConvention();
   private BusConfiguration configuration;

   public BusBuilder() {
   }

   public static BusBuilder newBuilder() {
      return new BusBuilder();
   }

   public BusBuilder handlers() {
      return this;
   }

   public BusBuilder inMemorySubscriptionStorage() {
      return subscriptionStorage(new InMemorySubscriptionStorage());
   }

   public BusBuilder subscriptionStorage(SubscriptionStorage subscriptionStorage) {
      this.subscriptionStorage = subscriptionStorage;
      return this;
   }

   public BusBuilder configuration(BusConfiguration configuration) {
      this.configuration = configuration;
      return this;
   }

   public Bus build() {
      try {
         ApplicationContext applicationContext = null;
         TopologyConfiguration topologyConfiguration = new TopologyConfiguration(configuration);
         SpringCamelContext camelContext = new SpringCamelContext(applicationContext);
         camelContext.setComponentResolver(new CodonComponentResolver(transactionConvention, configuration));
         camelContext.afterPropertiesSet();
         BusContextProvider contextProvider = new ConstantBusContextProvider(new BusContext(topologyConfiguration, subscriptionStorage));
         InvokeHandlerProcessor invokeHandlerProcessor = new InvokeHandlerProcessor(handlerRegistry, contextProvider);
         Transport transport = new DefaultCamelTransport(configuration, camelContext, invokeHandlerProcessor);
         return new DefaultBus(contextProvider, transport);
      }
      catch(Exception e) {
         throw new RuntimeException(e);
      }
   }
}