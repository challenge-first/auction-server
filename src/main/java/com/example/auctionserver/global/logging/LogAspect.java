package com.example.auctionserver.global.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class LogAspect {


    @Pointcut("execution(* com.example.auctionserver.adapter.in..*(..))")
    public void controller() {}

    @Pointcut("execution(* com.example.auctionserver.application.service..*(..))")
    public void service() {}

    @Before("controller()")
    public void logControllerMethodCall(JoinPoint joinPoint) {

        log.info("[{}]" , joinPoint.getSignature().getName());
    }

    @AfterThrowing

    @Before("service()")
    public void logServiceMethodCall(JoinPoint joinPoint) {

        log.info("[{}]" , joinPoint.getSignature().getName());
    }
}
