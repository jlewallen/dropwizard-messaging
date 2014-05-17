package com.page5of4.codon;

import java.io.Serializable;

public class EndpointAddress implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String address;
    private final String[] parts;

    public EndpointAddress(String address) {
        super();
        this.address = address;
        this.parts = address.split(":");
        if (parts.length != 2) throw new BusException(String.format("Malformed EndpointAddress: '%s'", address));
    }

    public String getPath() {
        return parts[1];
    }

    public String getHost() {
        return parts[0];
    }

    @Override
    public String toString() {
        return address;
    }

    @Override
    public int hashCode() {
        return address.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        return address.equals(((EndpointAddress) obj).address);
    }
}
