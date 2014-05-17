package com.page5of4.codon.subscriptions;

import com.page5of4.codon.EndpointAddress;

public class Subscription {
    private String address;
    private String messageType;

    public Subscription() {
        super();
    }

    public Subscription(String address, String messageType) {
        super();
        this.address = address;
        this.messageType = messageType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public EndpointAddress toEndpointAddress() {
        return new EndpointAddress(getAddress());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hash = 1;
        hash = prime * hash + address.hashCode();
        hash = prime * hash + messageType.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Subscription other = (Subscription) obj;
        if (!address.equals(other.address)) return false;
        if (!messageType.equals(other.messageType)) return false;
        return true;
    }

    @Override
    public String toString() {
        return "Subscription [address=" + address + ", messageType=" + messageType + "]";
    }
}
