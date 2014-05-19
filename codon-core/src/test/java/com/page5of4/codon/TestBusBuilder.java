package com.page5of4.codon;

import com.page5of4.codon.camel.DefaultCamelTransport;
import com.page5of4.codon.camel.InvokeHandlerProcessor;
import com.page5of4.codon.impl.ApplicationContextResolver;
import com.page5of4.codon.impl.BusContext;
import com.page5of4.codon.impl.BusContextProvider;
import com.page5of4.codon.impl.ConstantBusContextProvider;
import com.page5of4.codon.impl.DefaultBus;
import com.page5of4.codon.impl.InstanceResolver;
import com.page5of4.codon.impl.MessageUtils;
import com.page5of4.codon.impl.SpringHandlerRegistry;
import com.page5of4.codon.impl.TopologyConfiguration;
import com.page5of4.codon.subscriptions.Subscription;
import com.page5of4.codon.subscriptions.impl.InMemorySubscriptionStorage;
import org.apache.camel.CamelContext;
import org.apache.camel.model.ModelCamelContext;

import java.util.Arrays;

public class TestBusBuilder {
   private final ModelCamelContext camelContext;
   private final InstanceResolver resolver = new ApplicationContextResolver(null);
   private final HandlerRegistry handlerRegistry = new SpringHandlerRegistry(null, resolver);
   private final InMemorySubscriptionStorage subscriptionStorage = new InMemorySubscriptionStorage();

   public TestBusBuilder(ModelCamelContext camelContext) {
      this.camelContext = camelContext;
   }

   public static TestBusBuilder make(CamelContext camelContext) {
      return new TestBusBuilder((ModelCamelContext)camelContext);
   }

   public TestBusBuilder subscribed(String uri, Class<?> messageType) {
      subscriptionStorage.addSubscriptions(Arrays.asList(new Subscription(uri, MessageUtils.getMessageType(messageType))));
      return this;
   }

   public Bus build() {
      PropertiesConfiguration configuration = new PropertiesConfiguration("test", "testing-server");
      TopologyConfiguration topologyConfiguration = new TopologyConfiguration(configuration);
      BusContextProvider contextProvider = new ConstantBusContextProvider(new BusContext(topologyConfiguration, subscriptionStorage));
      return new DefaultBus(contextProvider, new DefaultCamelTransport(configuration, camelContext, new InvokeHandlerProcessor(handlerRegistry, contextProvider)));
   }
}
