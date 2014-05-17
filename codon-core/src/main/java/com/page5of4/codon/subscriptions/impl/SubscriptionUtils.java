package com.page5of4.codon.subscriptions.impl;

import com.page5of4.codon.EndpointAddress;
import com.page5of4.codon.subscriptions.Subscription;

import java.util.ArrayList;
import java.util.List;

public abstract class SubscriptionUtils {
    public static List<EndpointAddress> filter(List<Subscription> subscriptions, String messageType) {
        List<EndpointAddress> addresses = new ArrayList<EndpointAddress>();
        for (Subscription subscription : subscriptions) {
            if (subscription.getMessageType().equalsIgnoreCase(messageType)) {
                addresses.add(subscription.toEndpointAddress());
            }
        }
        return addresses;
    }
}
