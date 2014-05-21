package com.page5of4.codon;

import com.page5of4.codon.impl.BusConfigurationTopologyConfiguration;
import com.page5of4.codon.impl.TopologyConfiguration;
import com.page5of4.codon.subscriptions.messages.SubscribeMessage;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.fest.assertions.Assertions.assertThat;

public class BusConfigurationTopologyConfigurationSpecs {
   private TopologyConfiguration topology;

   @Before
   public void before() {
      PropertiesConfiguration configuration = new PropertiesConfiguration("test", "testing-server");
      Map<String, String> properties = new HashMap<String, String>();
      properties.put("bus.owner.com.page5of4.codon", "server:remote.{messageType}");
      configuration.addProperties(properties);
      topology = new BusConfigurationTopologyConfiguration(configuration);
   }

   @Test(expected = BusException.class)
   public void when_getting_owner_thats_missing_should_throw() {
      topology.getOwner(Long.class);
   }

   @Test
   public void when_getting_owning_address_of_message() {
      assertThat(topology.getOwner(ExampleMessage.class)).isEqualTo(new EndpointAddress("server:remote.com.page5of4.codon.TopologyConfigurationSpecs$ExampleMessage"));
   }

   @Test
   public void when_getting_local_address_of_message() {
      assertThat(topology.getLocalAddressOf(ExampleMessage.class)).isEqualTo(new EndpointAddress("testing-server:test.com.page5of4.codon.TopologyConfigurationSpecs$ExampleMessage"));
   }

   @Test
   public void when_getting_subscription_address_of_message() {
      assertThat(topology.getSubscriptionAddressOf(ExampleMessage.class, SubscribeMessage.class)).isEqualTo(new EndpointAddress("server:remote.com.page5of4.codon.subscriptions.messages.SubscribeMessage"));
   }

   public static class ExampleMessage {
   }
}
