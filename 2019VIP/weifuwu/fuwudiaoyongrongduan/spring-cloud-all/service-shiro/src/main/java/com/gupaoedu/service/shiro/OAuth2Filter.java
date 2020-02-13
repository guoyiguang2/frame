package com.gupaoedu.service.shiro;



import com.gupaoedu.service.exception.ResponseUtil;
import com.gupaoedu.service.utils.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.shiro.authc.AuthenticationToken;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.authc.AuthenticationException;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 自定义认证过滤器
 *  参考链接
 * https://blog.csdn.net/weixin_40663800/article/details/86287995
 *
 */
public class OAuth2Filter  extends  AuthenticatingFilter{


    private final static Logger logger = LoggerFactory.getLogger(OAuth2Filter.class);
    @Autowired
    public RedisUtil redisUtil;

    /**
     * 过滤器每个请求都会走
     * 封装前端传过来的token或者封装前端传过来的原始登录名和登录密码（redis中存的是加密后的）
     * shir进行认证需要 这个token作为用户提交的 用户名和密码（其实主要还是比对密码）
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        String host = request.getRemoteHost();
        // HttpServletRequestWrapper  是 ShiroHttpServletRequest  父类
        // (HttpServletRequest)request  这样转有问题，因为  ShiroHttpServletRequest（这儿断点走的实现类）不是HttpServletRequest的子类
        System.out.println( ((ShiroHttpServletRequest)request).getRequestURI());
        String requestUrl =   ((ShiroHttpServletRequest)request).getRequestURI();
        if(!StringUtils.isBlank(requestUrl) && requestUrl.contains("/login")){
            String username = request.getParameter("loginName");
            String password = request.getParameter("password");

            return new UsernamePasswordToken(username, password, false, host);
        }else{
            String token = getRequestToken(request);
            if(!StringUtils.isBlank(token)){
                // 获得用户信息，包括用户登录存入的  name 和 password 加密后的值
                String credentialFromRedis = (String)redisUtil.getValue("666666_ABCDEFG");
                if(StringUtils.isBlank(credentialFromRedis)){
                    // token已经失效
                    throw new IncorrectCredentialsException("token失效，请重新登录");
                }
                // 完成解密操作获得 原始的登录名和用户登录密码
                String loginName = "admin";
                String loginPassword = "123456";
                return new UsernamePasswordToken(loginName, loginPassword, false, host);
            }else{
                //给前端返回错误信息
                 ResponseUtil.responseContent((HttpServletResponse)response,"403","invalid token");
                 logger.info("createToken method invalid token");
                 // 终止程序
                 throw new IncorrectCredentialsException("token失效，invalid token");
            }
        }
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        return false;
    }

    /**
     *
     *授权失败走的方法
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        // 获取请求token，如果token不存在，直接返回401
        String token = getRequestToken(request);
        if(StringUtils.isBlank(token)){
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            Map<Integer,String> result = new HashMap<Integer,String>();
            result.put(HttpStatus.SC_UNAUTHORIZED,"invalid token");
            String json = JSONObject.toJSONString(result);
            httpResponse.getWriter().print(json);
            return false;
        }
        boolean flag = false;
        try{
            //执行认证的入口(不授权)
            flag = executeLogin(request, response);
            //登录失败提示  在 onLoginFailure 方法中给出提示
        }catch (Exception ex){
            System.out.println(ex);
        }
        return flag;
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        // 处理登录失败的异常
        Throwable throwable = e.getCause() == null ? e : e.getCause();
        //给前端输出文本
        ResponseUtil.responseContent((HttpServletResponse)response,"403", throwable.getMessage());
        return false;
    }

    /**
     * 获取请求的token
     */
    private String getRequestToken(HttpServletRequest httpRequest){
        // 从header中获取token
        String token = httpRequest.getHeader("token");
        // 如果header中不存在token，则从参数中获取token
        if(StringUtils.isBlank(token)){
            token = httpRequest.getParameter("token");
        }
        return token;
    }

    /**
     * 获取请求的token
     */
    private String getRequestToken(ServletRequest request){
        // 从header中获取token
        String token =   ((ShiroHttpServletRequest)request).getHeader("token");
        // 如果header中不存在token，则从参数中获取token
        if(StringUtils.isBlank(token)){
            token = request.getParameter("token");
        }
        return "666666_ABCDEFG";
        //return token;
    }

}
