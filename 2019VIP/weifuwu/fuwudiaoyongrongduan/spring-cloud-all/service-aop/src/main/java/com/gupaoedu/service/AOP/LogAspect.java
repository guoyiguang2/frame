package com.gupaoedu.service.AOP;

import com.gupaoedu.service.exception.BusinessException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 *AOP记录访问日志
 * 2020年2月5日14:06:44
 */
@Aspect
@Component
@Order(1)
public class LogAspect {

    private final static Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Pointcut("execution(public * com.gupaoedu.service.*.*(..))")
    public void log() {
    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        //强转 才能 proceed
        ProceedingJoinPoint pjp = (ProceedingJoinPoint)joinPoint;
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //url method ip
        logger.info("利用AOP记录每次请求的有关信息，url={}，method={}，ip={}", request.getRequestURL(), request.getMethod(), request.getRemoteAddr());

        //类方法 参数
        logger.info("class_method={}, args={}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName(), joinPoint.getArgs());
        try {
            pjp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            throw new BusinessException("500","AOP报错");
        }
    }

    @After("log()")
    public void doAfter() {
        logger.info("利用AOP记录每次请求完成后的信息");
    }

    @AfterReturning(returning = "object", pointcut = "log()")
    public void doAfterReturning(Object object) {
        logger.info("response={}", object.toString());
    }

}
