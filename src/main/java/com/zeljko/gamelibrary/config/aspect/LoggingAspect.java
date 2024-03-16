package com.zeljko.gamelibrary.config.aspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    @Pointcut("within(com.zeljko.gamelibrary.controller..*)")
    public void endpointMethods() {}
    @Before("endpointMethods()")
    public void logBefore(JoinPoint joinPoint) {
        log.info("Started executing method: {}() in controller: {} with method arguments: {}",
                joinPoint.getSignature().getName(),
                joinPoint.getTarget().getClass().getSimpleName(),
                Arrays.toString(joinPoint.getArgs())
        );
    }
    @AfterReturning("endpointMethods()")
    public void logAfter(JoinPoint joinPoint) {
        log.info("Successfully executed method: {}() from controller: {}",
                joinPoint.getSignature().getName(),
                joinPoint.getTarget().getClass().getSimpleName());
    }
}
