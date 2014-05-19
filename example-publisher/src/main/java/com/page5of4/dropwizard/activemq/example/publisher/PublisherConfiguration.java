package com.page5of4.dropwizard.activemq.example.publisher;

import com.page5of4.codon.dropwizard.ConfiguresCodon;
import com.page5of4.dropwizard.activemq.BrokerConfiguration;
import com.page5of4.dropwizard.activemq.ConfiguresMessageQueuing;
import io.dropwizard.Configuration;

public class PublisherConfiguration extends Configuration implements ConfiguresMessageQueuing, ConfiguresCodon {
   private BrokerConfiguration brokerConfiguration = new BrokerConfiguration();

   @Override
   public BrokerConfiguration getBrokerConfiguration() {
      return brokerConfiguration;
   }
}
