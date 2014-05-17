package com.page5of4.codon.impl;

public abstract class MessageUtils {
    public static String getMessageType(Class<?> messageType) {
        return messageType.getName();
    }

    public static String getMessageType(Object message) {
        return getMessageType(message.getClass());
    }
}
