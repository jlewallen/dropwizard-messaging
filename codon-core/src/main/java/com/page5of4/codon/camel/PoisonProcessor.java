package com.page5of4.codon.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class PoisonProcessor implements Processor, Predicate {
    private static final long RETRIES = 3;
    private static final Logger logger = LoggerFactory.getLogger(PoisonProcessor.class);
    private static final Map<String, AtomicLong> failures = new ConcurrentHashMap<String, AtomicLong>();

    @Override
    public void process(Exchange exchange) throws Exception {
        String key = getKey(exchange);
        Throwable cause = exchange.getException();
        logger.debug("Exchange: {} {}", exchange, exchange.getIn().getHeaders());
        if (cause == null) {
            cause = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Throwable.class);
        }
        if (cause != null) {
            if (!failures.containsKey(key)) {
                failures.put(key, new AtomicLong());
            }
            long value = failures.get(key).incrementAndGet();
            logger.debug("Error, increasing: {} = {}", key, value);
        } else {
            logger.debug("Removing {}", key);
            failures.remove(key);
        }
    }

    @Override
    public boolean matches(Exchange exchange) {
        String key = getKey(exchange);
        if (failures.containsKey(key) && failures.get(key).get() >= RETRIES) {
            failures.remove(key);
            return true;
        }
        return false;
    }

    private String getKey(Exchange exchange) {
        return exchange.getIn().getHeader("JMSMessageID", String.class);
    }
}
