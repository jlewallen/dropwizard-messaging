package com.page5of4.codon;

public class NullBus implements Bus {
    @Override
    public <T> void publish(T message) {

    }

    @Override
    public <T> void send(T message) {

    }

    @Override
    public <T> void sendLocal(T message) {

    }

    @Override
    public <T> void send(EndpointAddress address, T message) {

    }

    @Override
    public void subscribe(Class<?> messageType) {

    }

    @Override
    public void unsubscribe(Class<?> messageType) {

    }

    @Override
    public void listen(Class<?> messageType) {

    }

    @Override
    public void unlisten(Class<?> messageType) {

    }
}
