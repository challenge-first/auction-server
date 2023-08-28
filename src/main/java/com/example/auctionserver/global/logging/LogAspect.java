package com.example.auctionserver.global.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class LogAspect {


    @Pointcut("execution(* com.example.auctionserver.auction..*(..))")
    public void all() {}

    @Before("all()")
    public void logAll(JoinPoint joinPoint) {

        log.info("[{}]" , joinPoint.getSignature().getName());
    }
}
