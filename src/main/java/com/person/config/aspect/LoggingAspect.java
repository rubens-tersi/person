package com.person.config.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {

    @Around("execution(* com.autopass.person..*.*(..))")
    public Object loggingAroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        final long startTime = System.nanoTime();
        Logger log = LoggerFactory.getLogger(proceedingJoinPoint.getTarget().getClass());

        if (hasParams(proceedingJoinPoint)) {
            log.info("Invoking method \"{}\" with params: {}", proceedingJoinPoint.getSignature().getName(), proceedingJoinPoint.getArgs());
        } else {
            log.info("Invoking method \"{}\"", proceedingJoinPoint.getSignature());
        }

        Object o = proceedWith(proceedingJoinPoint, log);

        final long endTime = System.nanoTime();
        log.info("Method \"{}\" finished in {} ns.", proceedingJoinPoint.getSignature().getName(), (endTime - startTime) / 1e6);

        return o;
    }

    private Object proceedWith(ProceedingJoinPoint proceedingJoinPoint, Logger log) throws Throwable {
        try {
            return proceedingJoinPoint.proceed();

        } catch (Throwable e) {
            if (hasParams(proceedingJoinPoint)) {
                log.error("Exception while executing method \"{}\" with params: {}", proceedingJoinPoint.getSignature().getName(),
                        proceedingJoinPoint.getArgs());
            } else {
                log.warn("Exception while executing method \"{}\"", proceedingJoinPoint.getSignature().getName());
            }
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    private boolean hasParams(ProceedingJoinPoint proceedingJoinPoint) {
        return proceedingJoinPoint.getArgs() != null;
    }

}
