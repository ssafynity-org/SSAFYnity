package com.ssafynity_b.global.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class ControllerLoggingAspect {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    //컨트롤러 클래스에만 적용
    @Around("execution(* com.ssafynity_b..*Controller.*(..))")
    public Object logController(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        log.info("▶ 컨트롤러 진입: {}", methodName);

        try{
            Object result = joinPoint.proceed();
            log.info("● 컨트롤러 정상 종료: {}", methodName);
            return result;
        } catch (Throwable e){
            log.error("X 컨트롤러 예외 발생: {}", methodName, e);
            throw e;
        }
    }
}
