package com.bbco.practice.web.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class TraceAspect {

    @Around("com.bbco.practice.web.aop.pointcut.AnnotationPointcut.runTimeTrace()")
    public Object doTrace(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        try {
            log.info("[실행 메서드 정보] : {}", joinPoint.getSignature());

            return joinPoint.proceed();
        } finally {
            long endTime = System.currentTimeMillis();

            log.info("[소요 시간] : {} ms", endTime - startTime);
        }
    }
}
