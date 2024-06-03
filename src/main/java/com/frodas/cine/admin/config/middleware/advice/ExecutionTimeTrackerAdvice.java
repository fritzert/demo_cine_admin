package com.frodas.cine.admin.config.middleware.advice;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ExecutionTimeTrackerAdvice {

    @Around("@annotation(com.frodas.cine.admin.config.middleware.advice.TrackExecutionTime)")
    public Object trackTime(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object obj = pjp.proceed();
        long endTime = System.currentTimeMillis();
        log.info("Method name" + pjp.getSignature() + " time taken to execute : " + (endTime - startTime));
        return obj;
    }

}
