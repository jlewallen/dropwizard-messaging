package com.page5of4.codon.dropwizard;

import com.google.common.collect.Lists;
import com.page5of4.codon.BusConfiguration;
import com.page5of4.codon.subscriptions.Subscription;
import com.page5of4.codon.subscriptions.SubscriptionStorage;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.List;

@Path("/codon")
@Produces(MediaType.APPLICATION_JSON)
public class CodonResource {
   private BusConfiguration configuration;
   private SubscriptionStorage subscriptionStorage;

   public CodonResource (BusConfiguration configuration, SubscriptionStorage subscriptionStorage) {
      this.configuration = configuration;
      this.subscriptionStorage = subscriptionStorage;
   }

   @GET
   public DiagnosticsInfo diagnostics() {
      return new DiagnosticsInfo(configuration, subscriptionStorage.findAllSubscriptions());
   }

   public static class DiagnosticsInfo  {
      private BusConfiguration configuration;
      private List<Subscription> subscriptions;

      public BusConfiguration getConfiguration() {
         return configuration;
      }

      public List<Subscription> getSubscriptions() {
         return subscriptions;
      }

      public DiagnosticsInfo(BusConfiguration configuration, Collection<Subscription> subscriptions) {
         this.configuration = configuration;
         this.subscriptions = Lists.newArrayList(subscriptions);
      }
   }
}
