package com.page5of4.codon.dropwizard;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableMap;
import com.page5of4.codon.Bus;
import com.page5of4.codon.BusConfiguration;
import com.page5of4.codon.PropertiesConfiguration;
import com.page5of4.codon.utils.LocalIpAddress;
import com.page5of4.dropwizard.activemq.BrokerConfiguration;

import javax.validation.constraints.NotNull;
import java.util.Map;

public class CodonConfiguration {
   @NotNull
   @JsonProperty
   private ImmutableMap<String, String> owners = ImmutableMap.of();

   public ImmutableMap<String, String> getOwners() {
      return owners;
   }

   public void setOwners(ImmutableMap<String, String> owners) {
      this.owners = owners;
   }

   @NotNull
   @JsonProperty
   private String name;

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   @JsonProperty(value = "broker")
   private BrokerConfiguration brokerConfiguration = new BrokerConfiguration();

   public BrokerConfiguration getBrokerConfiguration() {
      return brokerConfiguration;
   }

   public void setBrokerConfiguration(BrokerConfiguration brokerConfiguration) {
      this.brokerConfiguration = brokerConfiguration;
   }

   public BusConfiguration createBusConfiguration(BrokerConfiguration brokerConfiguration) {
      Integer port = brokerConfiguration.getPort();
      String localBrokerUrl = "tcp://" + LocalIpAddress.guessLocalIp().getHostAddress() + ":" + port;
      PropertiesConfiguration properties = new PropertiesConfiguration(getName(), localBrokerUrl);
      for(Map.Entry<String, String> owner : getOwners().entrySet()) {
         properties.setOwner(owner.getKey(), owner.getValue());
      }
      return properties;
   }

   private Bus bus;

   public Bus getBus() {
      return bus;
   }

   public void setBus(Bus bus) {
      this.bus = bus;
   }
}
