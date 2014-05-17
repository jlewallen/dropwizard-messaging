package com.page5of4.dropwizard.activemq.example.publisher;

import com.page5of4.dropwizard.activemq.ConfiguresMessageQueuing;
import io.dropwizard.Configuration;

public class PublisherConfiguration extends Configuration implements ConfiguresMessageQueuing {
    private String subscriptionStorageType;

    public String getSubscriptionStorageType() {
        return subscriptionStorageType;
    }

    public void setSubscriptionStorageType(String subscriptionStorageType) {
        this.subscriptionStorageType = subscriptionStorageType;
    }
}
