package com.page5of4.dropwizard.activemq.example.publisher;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.page5of4.codon.dropwizard.CodonConfiguration;
import com.page5of4.codon.dropwizard.ConfiguresCodon;
import com.page5of4.dropwizard.activemq.BrokerConfiguration;
import com.page5of4.dropwizard.activemq.ConfiguresMessageQueuing;
import com.page5of4.dropwizard.discovery.zookeeper.ConfiguresZooKeeper;
import com.page5of4.dropwizard.discovery.zookeeper.ZooKeeperConfiguration;
import io.dropwizard.Configuration;

public class PublisherConfiguration extends Configuration implements ConfiguresMessageQueuing, ConfiguresCodon, ConfiguresZooKeeper {
   @JsonProperty("broker")
   private BrokerConfiguration brokerConfiguration = new BrokerConfiguration();
   @JsonProperty("codon")
   private CodonConfiguration codonConfiguration = new CodonConfiguration();
   @JsonProperty("zookeeper")
   private ZooKeeperConfiguration zookeeperConfiguration = new ZooKeeperConfiguration();

   @Override
   public BrokerConfiguration getBrokerConfiguration() {
      return brokerConfiguration;
   }

   @Override
   public ZooKeeperConfiguration getZooKeeper() {
      return zookeeperConfiguration;
   }

   @Override
   public CodonConfiguration getCodonConfiguration() {
      return codonConfiguration;
   }
}

