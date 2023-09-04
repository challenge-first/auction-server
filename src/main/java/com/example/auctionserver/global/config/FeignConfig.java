package com.example.auctionserver.global.config;

import feign.Capability;
import feign.Retryer;
import feign.micrometer.MicrometerCapability;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class FeignConfig {

    @Bean
    Retryer.Default retryer() {

        return new Retryer.Default(100L, TimeUnit.SECONDS.toMillis(3L), 3);
    }

    @Bean
    public Capability capability(final MeterRegistry registry) {
        return new MicrometerCapability(registry);
    }
}
