package com.gupaoedu.service.AOP;


import com.gupaoedu.service.exception.BusinessException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 *AOP转换参数
 * 转换参数AOP
 * 过使用AOP技术，我们可以在自定义的切面对切点扩展相应的操作，如打印日志，转换参数等。
 *
 * 生产中，为保证数据安全或者减少数据传输量，传递参数时，往往会对参数进行BASE64编码、非对称加密等操作，一般我们会封装一个公共类，需要编解码或加解密参数的时候调用一下，这会造成一定的代码冗余，以上，我们可以采用AOP技术，解放操作。
 *
 * 以对参数进行BASE64编解码为例，我们先定义切面、切点：
 * 链接：
 * https://my.oschina.net/niithub/blog/1925045
 */
@Aspect
@Component
@Order(5)
public class TransParamsAspect {

    private final static Logger logger = LoggerFactory.getLogger(TransParamsAspect.class);

    static final ObjectMapper MAPPER = new ObjectMapper();


    //待测试
    @Pointcut("execution(public * com.gupaoedu.service.TestController.*.*(..))")
    public void doOperation() {
        logger.info("参数检验AOP");
    }

    @Around("doOperation()")
    public Object doBefore(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] obj = joinPoint.getArgs();
        byte[] param = null;
        for (Object argItem : obj) {
            String convertData;
            if (argItem instanceof String) {
                param = Base64Utils.decode(String.valueOf(argItem).getBytes());
            }
            convertData = new String(param);
            Map map = MAPPER.readValue(convertData, Map.class);
            if(map.get("body") == null) {
                throw new BusinessException("-1", "参数错误");
            }
            obj[0] = MAPPER.writeValueAsString(map.get("body"));
        }
        return joinPoint.proceed(obj);
    }

    @AfterReturning(returning = "object", pointcut = "doOperation()")
    public void doAfterReturning(Object object) {
        logger.info("请求返回值【{}】", object.toString());
    }




}
