package com.page5of4.codon.camel;

import com.page5of4.codon.EndpointAddress;

public abstract class EndpointUri {
    public static String fromEndpointAddress(EndpointAddress address) {
        return String.format("%s:%s", address.getHost(), address.getPath());
    }

    public static EndpointAddress toEndpointAddress(String uri) {
        return new EndpointAddress(uri.replace("/", ""));
    }
}
