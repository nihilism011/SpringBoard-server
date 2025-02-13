package com.project.spboard.core.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    private static final String CYAN = "\u001B[36m";
    private static final String BOLD_GREEN = "\u001B[1;32m";
    private static final String WHITE = "\u001B[97m";
    private static final String BOLD_LIGHT_BLUE = "\u001B[1;94m";
    private static final String BOLD_LIGHT_YELLOW = "\u001B[1;93m";

    @Pointcut("execution(* com.project.spboard.**.controller..*(..))")
    private void cut() {
    }

    @Around("cut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = getMethod(joinPoint);
        log.info(WHITE + "method name = " + BOLD_GREEN + "{}" + WHITE + "." + BOLD_LIGHT_BLUE + "{}", method.getDeclaringClass().getSimpleName(), method.getName());

        Object[] args = joinPoint.getArgs();
        if (args.length == 0) log.info("no parameter");
        for (Object arg : args) {
            log.info(WHITE + "parameter type = " + BOLD_LIGHT_YELLOW + "{} " + WHITE + "value = " + BOLD_LIGHT_BLUE + "{}", arg.getClass().getSimpleName(), arg);
        }
        Object returnObj = joinPoint.proceed();

        log.info(WHITE + "return type = " + BOLD_LIGHT_YELLOW + "{} " + WHITE + "value = " + BOLD_LIGHT_BLUE + "{}", returnObj.getClass().getSimpleName(), returnObj);

        return returnObj;
    }

    private Method getMethod(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getMethod();
    }
}
