package com.page5of4.codon;

import com.page5of4.codon.impl.HandlerDescriptor;
import com.page5of4.codon.impl.HandlerInspector;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class HandlerInspectorSpecs {
    private final HandlerInspector inspector = new HandlerInspector(null);

    @Test
    public void when_handler_has_zero_handler_methods() {
        HandlerDescriptor descriptor = inspector.discoverBindings(EmptyHandler.class);
        assertThat(descriptor.getProblems()).containsOnly("Class 'com.page5of4.codon.HandlerInspectorSpecs$EmptyHandler' has no handler methods.");
        assertThat(descriptor.getBindings()).isEmpty();
    }

    @Test
    public void when_handler_has_invalid_handler_method() {
        HandlerDescriptor descriptor = inspector.discoverBindings(BadHandlerMethodHandler.class);
        assertThat(descriptor.getProblems()).containsOnly("Method 'com.page5of4.codon.HandlerInspectorSpecs$BadHandlerMethodHandler::handle' has no valid message parameters.", "Class 'com.page5of4.codon.HandlerInspectorSpecs$BadHandlerMethodHandler' has no handler methods.");
        assertThat(descriptor.getBindings()).isEmpty();
    }

    @Test
    public void when_handler_has_one_handler_methods() {
        HandlerDescriptor descriptor = inspector.discoverBindings(OneHandler.class);
        assertThat(descriptor.getProblems()).isEmpty();
        assertThat(descriptor.getBindings().size()).isEqualTo(1);
        assertThat(descriptor.getBindings().get(0).getMessageType()).isEqualTo(String.class);
    }

    @Test
    public void when_handler_has_two_handler_methods() {
        HandlerDescriptor descriptor = inspector.discoverBindings(TwoHandlers.class);
        assertThat(descriptor.getProblems()).isEmpty();
        assertThat(descriptor.getBindings().size()).isEqualTo(2);
    }

    @MessageHandler
    public static class EmptyHandler {
    }

    @MessageHandler
    public static class BadHandlerMethodHandler {
        @MessageHandler
        public void handle() {
        }
    }

    @MessageHandler
    public static class OneHandler {
        @MessageHandler
        public void handle(String message) {
        }
    }

    @MessageHandler
    public static class TwoHandlers {
        @MessageHandler
        public void handle(String message) {
        }

        @MessageHandler
        public void handle(Long message) {
        }
    }
}
