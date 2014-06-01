package com.page5of4.codon.dagger;

import com.page5of4.codon.Bus;
import com.page5of4.codon.BusConfiguration;
import com.page5of4.codon.BusEvents;
import com.page5of4.codon.BusModule;
import com.page5of4.codon.HandlerRegistry;
import com.page5of4.codon.Subscriber;
import com.page5of4.codon.camel.DefaultCamelTransport;
import com.page5of4.codon.camel.InvokeHandlerProcessor;
import com.page5of4.codon.impl.BusContext;
import com.page5of4.codon.impl.BusContextProvider;
import com.page5of4.codon.impl.ConstantBusContextProvider;
import com.page5of4.codon.impl.DefaultBus;
import com.page5of4.codon.impl.EventsCaller;
import com.page5of4.codon.impl.TopologyConfiguration;
import com.page5of4.codon.subscriptions.SubscriptionStorage;
import dagger.Lazy;
import dagger.Module;
import dagger.Provides;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.ModelCamelContext;

import javax.inject.Provider;
import java.util.Collection;

@Module(library = true, complete = false)
public class CodonModule {
   @Provides
   public HandlerRegistry provideHandlerRegistry(StaticHandlerProvider staticHandlerProvider) {
      return new DaggerHandlerRegistry(staticHandlerProvider, staticHandlerProvider);
   }

   @Provides
   public Bus provideBus(HandlerRegistry handlerRegistry, BusConfiguration configuration, TopologyConfiguration topologyConfiguration, SubscriptionStorage subscriptionStorage, final Lazy<Collection<BusEvents>> busEvents) {
      BusContextProvider contextProvider = new ConstantBusContextProvider(new BusContext(topologyConfiguration, subscriptionStorage));
      ModelCamelContext camelContext = new DefaultCamelContext();
      DefaultCamelTransport transport = new DefaultCamelTransport(configuration, camelContext, new InvokeHandlerProcessor(handlerRegistry, contextProvider));
      return new DefaultBus(contextProvider, transport, new EventsCaller(new Provider<Collection<BusEvents>>() {
         @Override
         public Collection<BusEvents> get() {
            return busEvents.get();
         }
      }));
   }

   @Provides
   public Subscriber provideSubscriber(HandlerRegistry handlerRegistry, Bus bus) {
      return new Subscriber(handlerRegistry, bus);
   }

   @Provides
   public BusModule provideBusModule(HandlerRegistry handlerRegistry, Bus bus, Subscriber subscriber, final Lazy<Collection<BusEvents>> busEventsProvider) {
      return new BusModule(handlerRegistry, bus, subscriber, new EventsCaller(new Provider<Collection<BusEvents>>() {
         @Override
         public Collection<BusEvents> get() {
            return busEventsProvider.get();
         }
      }));
   }
}
