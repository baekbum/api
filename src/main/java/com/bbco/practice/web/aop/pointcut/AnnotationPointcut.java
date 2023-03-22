package com.bbco.practice.web.aop.pointcut;

import org.aspectj.lang.annotation.Pointcut;

public class AnnotationPointcut {

    @Pointcut("@annotation(com.bbco.practice.web.common.annotation.Trace)")
    public void runTimeTrace() { }
}
