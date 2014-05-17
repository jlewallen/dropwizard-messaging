package com.page5of4.codon.impl;

import com.page5of4.codon.BusException;
import com.page5of4.codon.HandlerBinding;
import com.page5of4.codon.HandlerRegistry;
import com.page5of4.codon.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SpringHandlerRegistry implements HandlerRegistry {
    private static final Logger logger = LoggerFactory.getLogger(HandlerRegistry.class);
    private final List<HandlerDescriptor> descriptors = new ArrayList<HandlerDescriptor>();
    private final ApplicationContext applicationContext;
    private final InstanceResolver resolver;

    public SpringHandlerRegistry(ApplicationContext applicationContext, InstanceResolver resolver) {
        super();
        this.applicationContext = applicationContext;
        this.resolver = resolver;
    }

    @Override
    public void initialize() {
        List<Class<?>> classes = new ArrayList<Class<?>>();
        Map<String, Object> handlers = applicationContext.getBeansWithAnnotation(MessageHandler.class);
        for (Map.Entry<String, Object> entry : handlers.entrySet()) {
            classes.add(entry.getValue().getClass());
        }
        addAll(classes);
    }

    @Override
    public void addAll(List<Class<?>> classes) {
        HandlerInspector inspector = new HandlerInspector(resolver);
        List<String> problems = new ArrayList<String>();
        for (Class<?> klass : classes) {
            logger.info("Inspecting {}", klass);
            HandlerDescriptor descriptor = inspector.discoverBindings(klass);
            descriptors.add(descriptor);
            problems.addAll(descriptor.getProblems());
        }
        if (!problems.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (String s : problems) {
                sb.append("\n").append(s);
            }
            throw new BusException(String.format("Error starting Bus:%s", sb));
        }
    }

    @Override
    public List<HandlerBinding> getBindings() {
        List<HandlerBinding> bindings = new ArrayList<HandlerBinding>();
        for (HandlerDescriptor descriptor : descriptors) {
            for (HandlerBinding binding : descriptor.getBindings()) {
                bindings.add(binding);
            }
        }
        return bindings;
    }

    @Override
    public List<HandlerBinding> getBindingsFor(Class<? extends Object> messageType) {
        List<HandlerBinding> bindings = new ArrayList<HandlerBinding>();
        for (HandlerDescriptor descriptor : descriptors) {
            for (HandlerBinding binding : descriptor.getBindings()) {
                if (binding.getMessageType().isAssignableFrom(messageType)) {
                    bindings.add(binding);
                }
            }
        }
        return bindings;
    }
}
