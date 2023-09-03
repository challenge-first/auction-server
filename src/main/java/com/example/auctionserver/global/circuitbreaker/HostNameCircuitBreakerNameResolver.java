package com.example.auctionserver.global.circuitbreaker;

import feign.Target;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.CircuitBreakerNameResolver;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

@Configuration
@Slf4j
public class HostNameCircuitBreakerNameResolver implements CircuitBreakerNameResolver{

    @Override
    public String resolveCircuitBreakerName(String feignClientName, Target<?> target, Method method) {
        String url = target.url();
        try {
            return new URL(url).getHost();
        } catch (MalformedURLException e) {
            log.error("MalformedURLException: {}", url);
            return "default";
        }
    }
}
