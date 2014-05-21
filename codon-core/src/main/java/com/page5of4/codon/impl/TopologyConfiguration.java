package com.page5of4.codon.impl;

import com.page5of4.codon.EndpointAddress;

public interface TopologyConfiguration {
   EndpointAddress getLocalAddressOf(Class<?> message);

   EndpointAddress getOwner(Class<?> message);

   EndpointAddress getSubscriptionAddressOf(Class<?> otherMessage, Class<?> message);

}
