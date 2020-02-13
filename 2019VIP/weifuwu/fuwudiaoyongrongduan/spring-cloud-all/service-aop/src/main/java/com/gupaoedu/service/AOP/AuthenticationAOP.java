package com.gupaoedu.service.AOP;


import com.alibaba.fastjson.JSONObject;
import com.gupaoedu.service.entity.UserVo;
import com.gupaoedu.service.exception.AopException;
import com.gupaoedu.service.service.UserService;
import com.gupaoedu.service.utils.RedisUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Aspect
@Component
@Order(3)
public class AuthenticationAOP {

    private final static Logger logger = LoggerFactory.getLogger(AuthenticationAOP.class);


    @Autowired
    public UserService userService;

    @Autowired
    public RedisUtil redisUtil;

    @Pointcut("execution(public * com.gupaoedu.service.adminResource.*.*(..))")
    public void adminResourceAuthentication() {
    }

    @Before("adminResourceAuthentication()")
    public void doBefore(JoinPoint joinPoint) throws Exception ,AopException {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response = attributes.getResponse();
        //强转才能 跳转到下个流程
        ProceedingJoinPoint pjp = (ProceedingJoinPoint)joinPoint;

        //认证用户(后可以从数据库配置表获取：公共的方法少，可以放到表里，不在表里的就是需要登录后访问的的资源)
        Path publicResourcePath = Paths.get("F:\\project\\gupao\\2019\\2019VIP\\weifuwu\\fuwudiaoyongrongduan\\spring-cloud-all\\service-aop\\src\\main\\java\\com\\gupaoedu\\service\\AOP\\public_resource.txt");
        Path adminResourcePath = Paths.get("F:\\project\\gupao\\2019\\2019VIP\\weifuwu\\fuwudiaoyongrongduan\\spring-cloud-all\\service-aop\\src\\main\\java\\com\\gupaoedu\\service\\AOP\\admin_resource.txt");
        System.out.println(publicResourcePath);
        System.out.println(adminResourcePath);
        List<String> publicResourceLines =  Files.readAllLines(publicResourcePath);
        List<String> adminResourceLines =  Files.readAllLines(adminResourcePath);

        // 用户访问的url
        String url = request.getRequestURI();
        logger.info(url);
        System.out.println(url);
        String url0 =  request.getRequestURL().toString();
        logger.info(url0);
        System.out.println(url0);

        // 1. 非公共资源从redis 获取登录信息，为空，则跳转登录页面；如果是登录提交，进行用户名和密码校验，只有错误才抛异常或跳页面
        if(url.contains("/loginSubmit?")){
            if(null == request.getParameter("loginName") || null == request.getParameter("password")){
                throw  new AopException("403","用户名或者密码非空");
            }
            String loginName  =  request.getParameter("loginName");
            String password  =  request.getParameter("password");
            //获取用户信息(包括权限信息)
            UserVo userVo = userService.getUserFromDataBase(loginName);
            if(null == userVo){
                throw  new AopException("404","用户不存在");
            }
            if(!password.equals(userVo.getPassword())){
                throw  new AopException("405","密码错误");
            }
            //生成token，保存到redis（30分钟过期），返给前端
            String token  = UUID.randomUUID().toString().replace("-","");
            boolean flag =  redisUtil.setValue(token,userVo,30*60*1000L);
            //跳转首页
            if(flag){
                throw  new AopException("200","LOGIN SUCCESS !!");

            }else{
                throw  new AopException("500","redis服务器错误 !!");
            }

        }
        // url 不在公共资源中，进行认证(如果数据存在session中，直接用 session.getAttribute("activeUser") 就可以获得用户信息，redis的话得用token)
        if(!publicResourceLines.contains(url)){
           String token =  request.getParameter("token");
           if(null == token || "".equals(token)){
               throw  new AopException("401","用户未登录");
           }
            String tokenFromRedis =  redisUtil.getValue(token).toString();
            if(null == tokenFromRedis || "".equals(tokenFromRedis)){
                throw  new AopException("401","用户未登录");
            }

        }
        try {
           Object[]  paraObjs =  joinPoint.getArgs();
            if ( paraObjs == null){
                pjp.proceed();
            }else{
                pjp.proceed(paraObjs);
            }

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
    @After("adminResourceAuthentication()")
    public void doAfter() {

    }

    @AfterReturning(returning = "object", pointcut = "adminResourceAuthentication()")
    public void doAfterReturning(Object object) {
        logger.info("AuthenticationAOP response={}", object.toString());
    }



    @AfterThrowing(throwing="ex",pointcut = "adminResourceAuthentication()")
    public void doAfterThrowing(Throwable ex) {
    }


}
