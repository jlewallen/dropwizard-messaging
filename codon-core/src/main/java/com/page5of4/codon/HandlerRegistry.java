package com.page5of4.codon;

import java.util.List;

public interface HandlerRegistry {
    public void initialize();

    public void addAll(List<Class<?>> classes);

    public List<HandlerBinding> getBindings();

    public List<HandlerBinding> getBindingsFor(Class<? extends Object> messageType);
}
