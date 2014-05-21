package com.page5of4.dropwizard.activemq.example.subscriber;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.page5of4.codon.dropwizard.CodonConfiguration;
import com.page5of4.codon.dropwizard.ConfiguresCodon;
import com.page5of4.dropwizard.activemq.BrokerConfiguration;
import com.page5of4.dropwizard.activemq.ConfiguresMessageQueuing;
import com.page5of4.dropwizard.discovery.zookeeper.ConfiguresZooKeeper;
import com.page5of4.dropwizard.discovery.zookeeper.ZooKeeperConfiguration;
import io.dropwizard.Configuration;

public class SubscriberConfiguration extends Configuration implements ConfiguresMessageQueuing, ConfiguresCodon, ConfiguresZooKeeper {
   private BrokerConfiguration brokerConfiguration = new BrokerConfiguration();
   private CodonConfiguration codonConfiguration = new CodonConfiguration();
   private ZooKeeperConfiguration zooKeeperConfiguration = new ZooKeeperConfiguration();

   @Override
   @JsonProperty("broker")
   public BrokerConfiguration getBrokerConfiguration() {
      return brokerConfiguration;
   }

   @Override
   @JsonProperty("zookeeper")
   public ZooKeeperConfiguration getZooKeeperConfiguration() {
      return zooKeeperConfiguration;
   }

   @Override
   @JsonProperty("codon")
   public CodonConfiguration getCodonConfiguration() {
      return codonConfiguration;
   }
}
