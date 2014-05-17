package com.page5of4.codon.camel;

import com.page5of4.codon.BusConfiguration;
import com.page5of4.codon.BusConfiguration.ListenerConfiguration;
import com.page5of4.codon.BusException;
import com.page5of4.codon.EndpointAddress;
import com.page5of4.codon.Transport;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.model.ModelCamelContext;
import org.apache.camel.model.RoutesDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class DefaultCamelTransport implements Transport {
    private static final Logger logger = LoggerFactory.getLogger(DefaultCamelTransport.class);
    private final Map<EndpointAddress, ListeningOn> listenerMap = new ConcurrentHashMap<EndpointAddress, ListeningOn>();
    private final ModelCamelContext camelContext;
    private final ProducerTemplate producer;
    private final InvokeHandlerProcessor invokeHandlerProcessor;
    private final BusConfiguration configuration;

    public static final String MESSAGE_TYPE_KEY = "messageType";
    public static final String REPLY_TO_ADDRESS_KEY = "replyTo";

    public ModelCamelContext getCamelContext() {
        return camelContext;
    }

    @Autowired
    public DefaultCamelTransport(BusConfiguration configuration, ModelCamelContext camelContext, InvokeHandlerProcessor invokeHandlerProcessor) {
        this.configuration = configuration;
        this.camelContext = camelContext;
        this.invokeHandlerProcessor = invokeHandlerProcessor;
        this.producer = camelContext.createProducerTemplate();
    }

    @PostConstruct
    public void start() {
        try {
            camelContext.start();
        } catch (Exception e) {
            throw new BusException(e);
        }
    }

    @PreDestroy
    public void stop() {
        try {
            camelContext.stop();
        } catch (Exception e) {
            throw new BusException(e);
        }
    }

    @Override
    public void send(EndpointAddress address, Object message) {
        try {
            logger.debug("Sending {} -> {}", message, address);
            producer.send(EndpointUri.fromEndpointAddress(address), new OutgoingProcessor(message));
        } catch (Exception e) {
            throw new BusException(String.format("Unable to send '%s' to '%s'", message, address), e);
        }
    }

    @Override
    public void listen(EndpointAddress address) {
        try {
            synchronized (listenerMap) {
                if (!listenerMap.containsKey(address)) {
                    ListenerConfiguration listenerConfiguration = configuration.findListenerConfiguration(address.toString());
                    if (listenerConfiguration == null) {
                        throw new BusException(String.format("No ListenerConfiguration available for '%s'", address));
                    }
                    HandlerRouteBuilder builder = new HandlerRouteBuilder(listenerConfiguration, invokeHandlerProcessor);
                    ListeningOn listening = new ListeningOn(builder.getRouteCollection());
                    listenerMap.put(address, listening);
                    camelContext.addRoutes(builder);
                }
                ListeningOn listening = listenerMap.get(address);
                logger.info("{} listeners on {}", listening.increase(), address);
            }
        } catch (Exception e) {
            throw new BusException(String.format("Unable to listen on '%s'", address), e);
        }
    }

    @Override
    public void unlisten(EndpointAddress address) {
        try {
            synchronized (listenerMap) {
                if (!listenerMap.containsKey(address)) {
                    logger.warn("Not listening to {}", address);
                    return;
                }
                ListeningOn listening = listenerMap.get(address);
                int remaining = listening.decrease();
                logger.info("{} listeners on {}", remaining, address);
                if (remaining == 0) {
                    camelContext.removeRouteDefinitions(listening.getRoutes().getRoutes());
                    listenerMap.remove(address);
                }
            }
        } catch (Exception e) {
            throw new BusException(String.format("Unable to listen on '%s'", address), e);
        }
    }

    public static class ListeningOn {
        private final RoutesDefinition routes;
        private final AtomicInteger numberOfListeners = new AtomicInteger();

        public ListeningOn(RoutesDefinition routes) {
            super();
            this.routes = routes;
        }

        public RoutesDefinition getRoutes() {
            return routes;
        }

        public int increase() {
            return numberOfListeners.incrementAndGet();
        }

        public int getNumberOfListeners() {
            return numberOfListeners.get();
        }

        public int decrease() {
            return numberOfListeners.decrementAndGet();
        }
    }
}
