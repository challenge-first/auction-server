package com.example.auctionserver.adapter.in.web;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auctions/circuit")
public class CircuitBreakerController {

    private final CircuitBreakerRegistry circuitBreakerRegistry;

    @GetMapping("/close/{name}")
    public ResponseEntity<Void> close(@PathVariable String name) {
        circuitBreakerRegistry.circuitBreaker(name)
                .transitionToClosedState();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/open/{name}")
    public ResponseEntity<Void> open(@PathVariable String name) {
        circuitBreakerRegistry.circuitBreaker(name)
                .transitionToOpenState();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/status/{name}")
    public ResponseEntity<CircuitBreaker.State> status(@PathVariable String name) {
        CircuitBreaker.State state = circuitBreakerRegistry.circuitBreaker(name)
                .getState();
        return ResponseEntity.ok(state);
    }
}
