package com.page5of4.dropwizard.activemq.example.subscriber;

import com.page5of4.codon.Bus;
import com.page5of4.codon.BusConfiguration;
import com.page5of4.codon.BusEvents;
import com.page5of4.codon.BusModule;
import com.page5of4.codon.Subscriber;
import com.page5of4.codon.activmq.discovery.ActiveMqNetworkManager;
import com.page5of4.codon.dagger.CodonModule;
import com.page5of4.codon.dagger.StaticHandlerProvider;
import com.page5of4.codon.impl.BusConfigurationTopologyConfiguration;
import com.page5of4.codon.impl.TopologyConfiguration;
import com.page5of4.codon.subscriptions.SubscriptionStorage;
import com.page5of4.codon.subscriptions.impl.InMemorySubscriptionStorage;
import dagger.Lazy;
import dagger.Module;
import dagger.Provides;
import org.apache.activemq.broker.BrokerService;
import org.apache.curator.framework.CuratorFramework;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Collections;

@Module(injects = SubscriberModule.BusStarter.class, includes = CodonModule.class)
public class SubscriberModule {
   private final SubscriberConfiguration subscriberConfiguration;
   private final CuratorFramework curatorFramework;
   private final BrokerService brokerService;

   public SubscriberModule(SubscriberConfiguration subscriberConfiguration) {
      this.subscriberConfiguration = subscriberConfiguration;
      this.curatorFramework = subscriberConfiguration.getZooKeeper().getCurator();
      this.brokerService = subscriberConfiguration.getCodonConfiguration().getBroker().createBroker();
   }

   @Provides
   public LaunchWorkHandler provideLaunchWorkHandler(Bus bus) {
      return new LaunchWorkHandler(bus);
   }

   @Provides
   public StaticHandlerProvider provideStaticHandlerProvider(Lazy<LaunchWorkHandler> launchWorkHandler) {
      StaticHandlerProvider provider = new StaticHandlerProvider();
      provider.registerHandler(LaunchWorkHandler.class, launchWorkHandler);
      return provider;
   }

   @Provides
   public TopologyConfiguration provideTopologyConfiguration() {
      return new BusConfigurationTopologyConfiguration(provideBusConfiguration());
   }

   @Provides
   public BusConfiguration provideBusConfiguration() {
      return subscriberConfiguration.getCodonConfiguration().createBusConfiguration();
   }

   @Provides
   public SubscriptionStorage provideSubscriptionStorage() {
      return new InMemorySubscriptionStorage();
   }

   @Provides
   public ActiveMqNetworkManager provideNetworkManager(BusConfiguration busConfiguration, Subscriber subscriber) {
      return new ActiveMqNetworkManager(busConfiguration, subscriber, curatorFramework, brokerService);
   }

   @Provides
   public Collection<BusEvents> provideBusEvents(ActiveMqNetworkManager activeMqNetworkManager) {
      return Collections.<BusEvents>singletonList(activeMqNetworkManager);
   }

   public static class BusStarter {
      private BusModule busModule;

      @Inject
      public BusStarter(BusModule busModule) {
         this.busModule = busModule;
      }
   }
}
