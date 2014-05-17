package com.page5of4.codon.impl;

import com.page5of4.codon.subscriptions.SubscriptionStorage;

public class BusContext {
    private final TopologyConfiguration topologyConfiguration;
    private final SubscriptionStorage subscriptionStorage;

    public TopologyConfiguration getTopologyConfiguration() {
        return topologyConfiguration;
    }

    public SubscriptionStorage getSubscriptionStorage() {
        return subscriptionStorage;
    }

    public BusContext(TopologyConfiguration topologyConfiguration, SubscriptionStorage subscriptionStorage) {
        super();
        this.topologyConfiguration = topologyConfiguration;
        this.subscriptionStorage = subscriptionStorage;
    }
}
