package com.page5of4.codon;

import com.page5of4.codon.impl.BusContextProvider;
import com.page5of4.codon.impl.HandlerDispatcher;
import com.page5of4.codon.impl.InstanceResolver;
import org.apache.camel.Exchange;

import java.lang.reflect.Method;

public class HandlerBinding {
    private final Class<?> handlerType;
    private final Class<?> messageType;
    private final Method method;
    private final AutomaticallySubscribe automaticallySubscribe;
    private final InstanceResolver resolver;

    public boolean shouldSubscribe() {
        return automaticallySubscribe.shouldSubscribe();
    }

    public Class<?> getHandlerType() {
        return handlerType;
    }

    public Class<?> getMessageType() {
        return messageType;
    }

    public Method getMethod() {
        return method;
    }

    public HandlerBinding(Class<?> handlerType, Class<?> messageType, Method method, AutomaticallySubscribe automaticallySubscribe, InstanceResolver resolver) {
        super();
        this.handlerType = handlerType;
        this.messageType = messageType;
        this.method = method;
        this.automaticallySubscribe = automaticallySubscribe;
        this.resolver = resolver;
    }

    public void dispatch(Object body, Exchange exchange, BusContextProvider contextProvider) {
        new HandlerDispatcher(resolver, contextProvider, this).dispatch(body, exchange);
    }
}
