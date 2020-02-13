package com.gupaoedu.service.AOP;


import com.gupaoedu.service.exception.BusinessException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import java.util.Map;

/**
 * 链接：
 * 自定义缓存
 * https://my.oschina.net/zhaomin/blog/1518328
 */
@Aspect
@Component
@Order(6)
public class RedisCacheAspect {

    private final static Logger logger = LoggerFactory.getLogger(RedisCacheAspect.class);



    //待测试
    @Pointcut("execution(public * com.gupaoedu.service.TestController.*.*(..))")
    public void doOperation() {
        logger.info("参数检验AOP");
    }



    @AfterReturning(returning = "object", pointcut = "doOperation()")
    public void doAfterReturning(Object object) {
        logger.info("请求返回值【{}】", object.toString());
    }


}
