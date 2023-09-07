package com.example.auctionserver.global.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class Resilience4jConfig {
    @Bean
    public CircuitBreakerConfig circuitBreakerConfig() {
        return CircuitBreakerConfig.custom()
                .failureRateThreshold(40)
                .slowCallRateThreshold(40)
                .slowCallDurationThreshold(Duration.ofSeconds(6))
                .permittedNumberOfCallsInHalfOpenState(5)
                .maxWaitDurationInHalfOpenState(Duration.ofSeconds(3))
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                .slidingWindowSize(10)
                .minimumNumberOfCalls(20)
                .waitDurationInOpenState(Duration.ofSeconds(1))
                .build();
    }

    @Bean
    public CircuitBreakerRegistry circuitBreakerRegistry() {

        return CircuitBreakerRegistry.of(circuitBreakerConfig());
    }
}