package com.page5of4.codon.config;

import com.page5of4.codon.subscriptions.impl.SubscribeHandler;
import com.page5of4.codon.subscriptions.impl.UnsubscribeHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PublisherConfig {
    @Bean
    public SubscribeHandler subscribeMessageHandler() {
        return new SubscribeHandler();
    }

    @Bean
    public UnsubscribeHandler unsubscribeMessageHandler() {
        return new UnsubscribeHandler();
    }
}
