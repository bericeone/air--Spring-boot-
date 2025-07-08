package com.air.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LogAspect {
    
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);
    
    @Pointcut("execution(* com.air.service.*.*(..))")
    public void serviceMethod() {}
    
    @Around("serviceMethod()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        
        // 获取方法签名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String className = signature.getDeclaringType().getSimpleName();
        String methodName = signature.getName();
        String[] parameterNames = signature.getParameterNames();
        Object[] args = joinPoint.getArgs();
        
        // 记录方法调用开始
        logger.info("开始执行 {}.{} 方法", className, methodName);
        if (args != null && args.length > 0) {
            logger.info("方法参数：{}", Arrays.toString(args));
        }
        
        try {
            // 执行目标方法
            Object result = joinPoint.proceed();
            long endTime = System.currentTimeMillis();
            
            // 记录方法执行结果和执行时间
            logger.info("{}.{} 方法执行完成，耗时：{}ms", className, methodName, (endTime - startTime));
            if (result != null) {
                logger.debug("方法返回值：{}", result);
            }
            
            return result;
        } catch (Throwable e) {
            // 记录异常信息
            logger.error("{}.{} 方法执行异常：{}", className, methodName, e.getMessage(), e);
            throw e;
        }
    }
} 