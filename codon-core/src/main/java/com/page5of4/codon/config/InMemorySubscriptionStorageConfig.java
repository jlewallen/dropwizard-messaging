package com.page5of4.codon.config;

import com.page5of4.codon.subscriptions.SubscriptionStorage;
import com.page5of4.codon.subscriptions.impl.InMemorySubscriptionStorage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InMemorySubscriptionStorageConfig {
    @Bean
    public SubscriptionStorage subscriptionStorage() {
        return new InMemorySubscriptionStorage();
    }
}
