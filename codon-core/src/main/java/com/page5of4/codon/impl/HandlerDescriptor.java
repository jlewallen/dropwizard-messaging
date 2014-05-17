package com.page5of4.codon.impl;

import com.page5of4.codon.HandlerBinding;
import com.page5of4.codon.MessageHandler;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class HandlerDescriptor {
    private final Class<?> handlerType;
    private final List<String> problems = new ArrayList<String>();
    private final List<HandlerBinding> bindings = new ArrayList<HandlerBinding>();

    public HandlerDescriptor(Class<?> handlerType, InstanceResolver resolver) {
        super();
        this.handlerType = handlerType;
        MessageHandler classAnnotation = handlerType.getAnnotation(MessageHandler.class);
        for (Method method : handlerType.getMethods()) {
            MessageHandler methodAnnotation = method.getAnnotation(MessageHandler.class);
            if (methodAnnotation != null) {
                Class<?> messageType = getMessageTypeFromParameters(method);
                if (messageType != null) {
                    bindings.add(new HandlerBinding(handlerType, messageType, method, classAnnotation.autoSubscribe().or(methodAnnotation.autoSubscribe()), resolver));
                }
            }
        }
        if (bindings.isEmpty()) {
            problems.add(String.format("Class '%s' has no handler methods.", handlerType.getName()));
        }
    }

    private Class<?> getMessageTypeFromParameters(Method method) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        for (Class<?> type : parameterTypes) {
            return type;
        }
        problems.add(String.format("Method '%s::%s' has no valid message parameters.", handlerType.getName(), method.getName()));
        return null;
    }

    public List<String> getProblems() {
        return problems;
    }

    public Class<?> getHandlerType() {
        return handlerType;
    }

    public List<HandlerBinding> getBindings() {
        return bindings;
    }
}
