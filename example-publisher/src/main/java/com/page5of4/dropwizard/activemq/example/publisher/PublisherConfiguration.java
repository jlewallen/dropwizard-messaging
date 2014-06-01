package com.page5of4.dropwizard.activemq.example.publisher;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.page5of4.codon.dropwizard.CodonConfiguration;
import com.page5of4.codon.dropwizard.ConfiguresCodon;
import com.page5of4.dropwizard.discovery.zookeeper.ConfiguresZooKeeper;
import com.page5of4.dropwizard.discovery.zookeeper.ZooKeeperConfiguration;
import io.dropwizard.Configuration;

public class PublisherConfiguration extends Configuration implements ConfiguresCodon, ConfiguresZooKeeper {
   @JsonProperty("codon")
   private CodonConfiguration codonConfiguration = new CodonConfiguration();
   @JsonProperty("zookeeper")
   private ZooKeeperConfiguration zookeeperConfiguration = new ZooKeeperConfiguration();

   @Override
   public ZooKeeperConfiguration getZooKeeper() {
      return zookeeperConfiguration;
   }

   @Override
   public CodonConfiguration getCodonConfiguration() {
      return codonConfiguration;
   }
}

