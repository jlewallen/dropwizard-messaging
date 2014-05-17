package com.page5of4.codon.camel;

import com.page5of4.codon.impl.MessageUtils;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OutgoingProcessor implements Processor {
    private static final Logger logger = LoggerFactory.getLogger(OutgoingProcessor.class);
    private final Object message;

    public OutgoingProcessor(Object message) {
        this.message = message;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        Message in = exchange.getIn();
        in.setHeader(DefaultCamelTransport.MESSAGE_TYPE_KEY, MessageUtils.getMessageType(message));
        in.setHeader(DefaultCamelTransport.REPLY_TO_ADDRESS_KEY, "");
        in.setBody(message);
    }
}
