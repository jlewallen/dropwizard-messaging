package com.page5of4.codon.camel;

import com.page5of4.codon.Bus;
import org.apache.camel.Exchange;

public class PublishProcessor extends BusProcessor {
    public PublishProcessor(Bus bus) {
        super(bus);
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        getBus().publish(exchange.getIn().getBody());
    }
}