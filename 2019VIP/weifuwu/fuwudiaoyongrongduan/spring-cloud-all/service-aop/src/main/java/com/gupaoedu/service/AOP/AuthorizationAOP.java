package com.gupaoedu.service.AOP;


import com.gupaoedu.service.entity.PermissionVo;
import com.gupaoedu.service.entity.Role;
import com.gupaoedu.service.entity.UserVo;
import com.gupaoedu.service.exception.AopException;
import com.gupaoedu.service.service.UserService;
import com.gupaoedu.service.utils.HttpUtils;
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
import java.util.List;

@Aspect
@Component
@Order(4)
public class AuthorizationAOP {

    private final static Logger logger = LoggerFactory.getLogger(AuthorizationAOP.class);

    @Autowired
    public UserService userService;

    @Autowired
    public RedisUtil redisUtil;

    @Pointcut("execution(public * com.gupaoedu.service.adminResource.*.*(..))")
    public void adminResourceAuthorization() {
    }

    @Before("adminResourceAuthorization()")
    public void doBefore(JoinPoint joinPoint)throws Exception ,AopException {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response = attributes.getResponse();
        //强转才能 跳转到下个流程
        ProceedingJoinPoint pjp = (ProceedingJoinPoint)joinPoint;
        String token =  request.getParameter("token");
        Object userObject = userService.getUserFromRedisByToken(token);
        if(null == userObject){
           throw new AopException("401","用户未登录");
        }
        // 测试跳转到 另一个controller，并新增 一个token的值
        if(true){
            String newUrl =   HttpUtils.getLastAccessUrl(request,token);

        }
        //用户登录URL
        String url = request.getRequestURI();

        //获取用户信息包括权限，和当前用户登录的权限比较，不一样，抛异常
        UserVo userVo =  (UserVo)userObject;
         List<Role> roleList =  userVo.getRolesList();
         if(null != roleList || roleList.size()>0){
             for (Role role : roleList) {
                 List<PermissionVo> permissionVoList  =   role.getPermissions();
                 // ttl 判断剩余时间是不是小于10分钟，如果是 重新设置 新的token（或者在原来基础上延期：这个简单）
                 // 在aop生成的token 如何传给 controller ① request.setAttribute("token",token);  ② 在后面拼接新的参数，在controller接收
               if(true){
                   String newUrl =   HttpUtils.getLastAccessUrl(request,token);
               }


                 if(null != permissionVoList && permissionVoList.size()>0 && permissionVoList.contains(url)){
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
                 }else{
                     throw  new AopException("402","用户无权限");
                 }
             }
         }else{
             throw  new AopException("402","用户无权限");

         }


    }
    @After("adminResourceAuthorization()")
    public void doAfter() {
        logger.info("利用AOP记录每次请求完成后的信息");
    }

    @AfterReturning(returning = "object", pointcut = "adminResourceAuthorization()")
    public void doAfterReturning(Object object) {
        logger.info("AuthorizationAOP response={}", object.toString());
    }
}
