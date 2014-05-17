package com.page5of4.codon.tests.integration;

import com.page5of4.codon.Bus;
import com.page5of4.codon.tests.support.TestLoader;
import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.model.ModelCamelContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import static org.fest.assertions.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = TestLoader.class)
public class RedeliverySpecs {
    @Autowired
    Bus bus;
    @Autowired
    ModelCamelContext camelContext;
    @Autowired
    MessageAHandler handler;

    @Before
    public void before() {
        handler.when(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                throw new RuntimeException("Fake connection error");
            }
        });
    }

    @Test
    @DirtiesContext
    public void when_sending_message_locally_that_fails() throws Exception {
        NotifyBuilder after = new NotifyBuilder(camelContext).whenExactlyFailed(3).whenCompleted(1).create();

        bus.sendLocal(new MessageAMessage("Jacob"));

        assertThat(after.matches(5L, TimeUnit.SECONDS)).isTrue();
    }
}
