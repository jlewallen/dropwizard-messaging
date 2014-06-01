package com.page5of4.codon.dropwizard;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableMap;
import com.page5of4.codon.BusConfiguration;
import com.page5of4.codon.PropertiesConfiguration;
import com.page5of4.codon.utils.LocalIpAddress;
import com.page5of4.dropwizard.activemq.BrokerConfiguration;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Environment;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Map;

public class CodonConfiguration {
   @Valid
   @NotNull
   @JsonProperty
   private String name;

   @JsonProperty
   private boolean enabled = true;

   @JsonProperty
   private final ImmutableMap<String, String> owners = ImmutableMap.of();

   @Valid
   @JsonProperty("broker")
   private final BrokerConfiguration brokerConfiguration = new BrokerConfiguration();

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public boolean getEnabled() {
      return enabled;
   }

   public void setEnabled(boolean enabled) {
      this.enabled = enabled;
   }

   public ImmutableMap<String, String> getOwners() {
      return owners;
   }

   public BrokerConfiguration getBroker() {
      return brokerConfiguration;
   }

   private PropertiesConfiguration busConfiguration;

   public BusConfiguration createBusConfiguration() {
      if(busConfiguration == null) {
         Integer port = getBroker().getPort();
         String localBrokerUrl = "tcp://" + LocalIpAddress.guessLocalIp().getHostAddress() + ":" + port;
         busConfiguration = new PropertiesConfiguration(getName(), localBrokerUrl);
         for(Map.Entry<String, String> owner : getOwners().entrySet()) {
            busConfiguration.setOwner(owner.getKey(), owner.getValue());
         }
      }
      return busConfiguration;
   }

   private AnnotationConfigApplicationContext applicationContext;

   @JsonIgnore
   public ApplicationContext getApplicationContext() {
      return applicationContext;
   }

   public ApplicationContext createApplicationContext(Configuration configuration, Environment environment, Collection<Class<?>> configurationClasses) {
      if(applicationContext != null) {
         throw new RuntimeException("Application context already created.");
      }
      applicationContext = new AnnotationConfigApplicationContext();
      for (Class<?> configurationClass : configurationClasses) {
         applicationContext.register(configurationClass);
      }
      applicationContext.getBeanFactory().registerSingleton("dropwizardConfiguration", configuration);
      applicationContext.getBeanFactory().registerSingleton("dropwizardEnvironment", environment);
      applicationContext.refresh();
      return applicationContext;
   }
}
