package com.example.videogame_distribution_app.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* com.example.videogame_distribution_app.service.*.*(..))")
    public void logBeforeMethodExecution() {
        System.out.println("A method in the service layer is about to be called");
    }

    @Around("execution(* com.example.videogame_distribution_app.service.*.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long endTime = System.currentTimeMillis();
        System.out.println("Method " + joinPoint.getSignature() + " executed in " + (endTime - startTime) + " ms");

        return result;
    }
}
