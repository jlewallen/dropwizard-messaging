package com.page5of4.codon.tests.integration;

import com.page5of4.codon.Bus;
import com.page5of4.codon.PropertiesConfiguration;
import com.page5of4.codon.subscriptions.impl.InMemorySubscriptionStorage;
import com.page5of4.codon.subscriptions.messages.SubscribeMessage;
import com.page5of4.codon.subscriptions.messages.UnsubscribeMessage;
import com.page5of4.codon.tests.support.TestLoader;
import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.model.ModelCamelContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

import static org.fest.assertions.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = TestLoader.class)
public class SubscriptionSpecs {
   @Autowired
   Bus bus;
   @Autowired
   InMemorySubscriptionStorage subscriptionStorage;
   @Autowired
   PropertiesConfiguration configuration;
   @Autowired
   ModelCamelContext camelContext;

   @Test
   @DirtiesContext
   public void when_receiving_subscription_its_stored() {
      NotifyBuilder notify = new NotifyBuilder(camelContext).whenCompleted(1).create();

      bus.sendLocal(new SubscribeMessage("test:com.page5of4.test", MessageAMessage.class.getName()));

      assertThat(notify.matches(5L, TimeUnit.SECONDS)).isTrue();

      assertThat(subscriptionStorage.findAllSubscriptions().size()).isEqualTo(1);
      assertThat(subscriptionStorage.findAllSubscriptions().get(0).getMessageType()).isEqualTo(MessageAMessage.class.getName());
   }

   @Test
   @DirtiesContext
   public void when_receiving_unsubscribe_its_stored() {
      NotifyBuilder notify = new NotifyBuilder(camelContext).whenCompleted(2).create();

      bus.sendLocal(new SubscribeMessage("test:com.page5of4.test", MessageAMessage.class.getName()));
      bus.sendLocal(new UnsubscribeMessage("test:com.page5of4.test", MessageAMessage.class.getName()));

      assertThat(notify.matches(5L, TimeUnit.SECONDS)).isTrue();

      assertThat(subscriptionStorage.findAllSubscriptions()).isEmpty();
   }
}
