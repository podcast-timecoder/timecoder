package com.timecoder.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Around("@annotation(Logger)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        String name = joinPoint.getSignature().getName();

        System.out.println("About to execute " + name);

        Object proceed = joinPoint.proceed();

        System.out.println("Executed "+ name);
        return proceed;
    }

}
