package org.dbpedia.spotlight.core;

import feign.Request;
import feign.Retryer;
import org.dbpedia.spotlight.services.Modules;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfiguration {
    @Value("${feign.connectTimeout:60000}")
    private int connectTimeout;

    @Value("${feign.readTimeOut:60000}")
    private int readTimeout;

    @Bean
    public Request.Options options() {
        return new Request.Options(connectTimeout, readTimeout);
    }

    @Bean
    public Retryer retryer() {
        return Retryer.NEVER_RETRY;
    }
}
