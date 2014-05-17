package com.page5of4.codon;

public interface Bus {
    <T> void publish(T message);

    <T> void send(T message);

    <T> void sendLocal(T message);

    <T> void send(EndpointAddress address, T message);

    void subscribe(Class<?> messageType);

    void unsubscribe(Class<?> messageType);

    void listen(Class<?> messageType);

    void unlisten(Class<?> messageType);
}
