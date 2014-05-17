package com.page5of4.codon.tests.integration;

import com.page5of4.codon.Bus;
import com.page5of4.codon.tests.support.RouteUtils;
import com.page5of4.codon.tests.support.TestLoader;
import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.model.ModelCamelContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import static org.fest.assertions.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = TestLoader.class)
public class PoisonSpecs {
    @Autowired
    Bus bus;
    @Autowired
    ModelCamelContext camelContext;

    @Test
    @DirtiesContext
    public void when_sending_message_locally_that_fails() throws Exception {
        RouteUtils.find(camelContext, "listen:.+MessageAMessage").adviceWith(camelContext, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                weaveAddLast().throwException(new RuntimeException("Fake connection error"));
            }
        });

        NotifyBuilder after = new NotifyBuilder(camelContext).whenExactlyFailed(5).create();

        bus.sendLocal(new MessageAMessage("Jacob"));

        assertThat(after.matches(5L, TimeUnit.SECONDS)).isTrue();
    }

    @Test
    @DirtiesContext
    public void when_sending_message_locally_that_fails_the_once() throws Exception {
        RouteUtils.find(camelContext, "listen:.+MessageAMessage").adviceWith(camelContext, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                weaveAddLast()
                        .choice()
                        .when(header("JMSRedelivered").isEqualTo("false"))
                        .throwException(new RuntimeException("Fake connection error"))
                        .end();
            }
        });

        NotifyBuilder after = new NotifyBuilder(camelContext).whenExactlyFailed(1).and().whenExactlyCompleted(1).create();

        bus.sendLocal(new MessageAMessage("Jacob"));

        assertThat(after.matches(5L, TimeUnit.SECONDS)).isTrue();
    }

    @Test
    @DirtiesContext
    public void when_sending_message_locally_that_fails_the_twice() throws Exception {
        RouteUtils.find(camelContext, "listen:.+MessageAMessage").adviceWith(camelContext, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                weaveAddLast()
                        .choice()
                        .when(new Predicate() {
                            private final AtomicLong counter = new AtomicLong();

                            @Override
                            public boolean matches(Exchange exchange) {
                                return counter.incrementAndGet() <= 2;
                            }
                        })
                        .throwException(new RuntimeException("Fake connection error"))
                        .end();
            }
        });

        NotifyBuilder after = new NotifyBuilder(camelContext).whenExactlyFailed(2).and().whenExactlyCompleted(1).create();

        bus.sendLocal(new MessageAMessage("Jacob"));

        assertThat(after.matches(5L, TimeUnit.SECONDS)).isTrue();
    }
}
