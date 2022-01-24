package com.iljin.apiServer.core.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Aspect
@Component
public class LogAspect {

    @Before("execution(* iljin.framework.*.*Controller.*(..))")
    public void onBeforeHandler(JoinPoint joinPoint) {
        log.info("================== onBeforeThing");
    }

    @After("execution(* iljin.framework.*.*Controller.*(..))")
    public void onAfterHandler(JoinPoint joinPoint) {
        log.info("================== onAfterHandler");
    }

    @AfterReturning(pointcut = "execution(* iljin.framework.*.*Controller.*(..))", returning = "result")
    public void onAfterReturningHandler(JoinPoint joinpoint, Object result) {

        Optional<Object> resultOptional = Optional.ofNullable(result);

        log.info("================== onAfterReturning");

        if(resultOptional.isPresent()) {
            log.info("return: " + result.toString());
        } else {
            log.info("return null");
        }

    }

    @AfterThrowing(pointcut = "execution(* iljin.framework.*.*Controller.*(..))", throwing = "ex")
    public void onAfterThrowingHandler(JoinPoint joinPoint, Throwable ex) {
        log.info("================== onAfterThrowing");
        log.info("throwing: " + ex.toString());
    }
}
