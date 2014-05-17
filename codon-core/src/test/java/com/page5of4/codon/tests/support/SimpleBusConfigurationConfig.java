package com.page5of4.codon.tests.support;

import com.page5of4.codon.BusConfiguration;
import com.page5of4.codon.PropertiesConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SimpleBusConfigurationConfig {
    @Bean
    public BusConfiguration busConfiguration() {
        PropertiesConfiguration configuration = new PropertiesConfiguration("test", "activemq");
        configuration.put("bus.owner.com.page5of4.codon", "remote:remote.{messageType}");
        return configuration;
    }
}
