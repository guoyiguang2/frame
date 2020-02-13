package com.gupaoedu.service.shiro;

import com.alibaba.fastjson.JSONObject;
import com.gupaoedu.service.AOP.LogAspect;
import com.gupaoedu.service.entity.PermissionVo;
import com.gupaoedu.service.entity.Role;
import com.gupaoedu.service.entity.SysUserToken;
import com.gupaoedu.service.entity.UserVo;
import com.gupaoedu.service.exception.AopException;
import com.gupaoedu.service.exception.ResponseUtil;
import com.gupaoedu.service.service.UserService;
import org.apache.http.HttpStatus;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


class MyShiroRealm extends AuthorizingRealm {


    private final static Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);

    @Autowired
    public UserService userService;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info("————身份认证方法————");

        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        String username =  token.getUsername();
        //模拟从数据库获取数据
        UserVo user = userService.getUserFromDataBase(username);
        // 密码认证可以shiro做，但是对用户的这些判断需要 程序员去实现
        // 非空得自己判断，否则空指针
        if(null == user ){
            throw new AopException("401","用户名不正确!");
        }
        // 账号被锁定
        if(user.getStatus() == 2){
            throw new LockedAccountException("账号已被锁定,请联系管理员");
        }
        // 下面这段代码可以交给shiro去认证
//       if(null == token.getCredentials() || (!user.getPassword().equals(new String((char[]) token.getCredentials()))) ){
//           throw new RuntimeException("密码不正确!");
//       }
            return new SimpleAuthenticationInfo(token.getPrincipal(), user.getPassword(), getName());

    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        logger.info("———授权方法————");
        //获取根据前端输入登录用户名从后台获取用户信息
        String name = (String)principals.getPrimaryPrincipal();
        UserVo user = userService.getUserFromDataBase(name);
        //添加权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
            for (Role role : user.getRolesList()) {
            //添加角色
            simpleAuthorizationInfo.addRole(role.getName());
            for (PermissionVo permission:role.getPermissions()) {
            //添加权限
                simpleAuthorizationInfo.addStringPermission(permission.getUrl());
            }
          }

        //获得该用户角色
//        List<Role> roles = user.getRolesList();
//        Set<String> set = new HashSet<>();
//        //需要将 role 封装到 Set 作为 info.setRoles() 的参数
//        roles.forEach((role)->{set.add(role.getName());});
//        //设置该用户拥有的角色
//        simpleAuthorizationInfo.setRoles(set);
        return simpleAuthorizationInfo;
    }

}
