package com.page5of4.codon.config;

import com.page5of4.codon.HandlerRegistry;
import com.page5of4.codon.impl.ApplicationContextResolver;
import com.page5of4.codon.impl.SpringHandlerRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfig {
    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public HandlerRegistry handlerRegistry() {
        return new SpringHandlerRegistry(applicationContext, new ApplicationContextResolver(applicationContext));
    }
}
