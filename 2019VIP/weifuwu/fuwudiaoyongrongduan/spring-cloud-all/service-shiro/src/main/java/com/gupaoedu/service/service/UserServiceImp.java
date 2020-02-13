package com.gupaoedu.service.service;

import com.gupaoedu.service.entity.PermissionVo;
import com.gupaoedu.service.entity.Role;
import com.gupaoedu.service.entity.SysUserToken;
import com.gupaoedu.service.entity.UserVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service
public class UserServiceImp implements UserService {

    @Override
    public UserVo getUserFromDataBase(String loginName) {
        if (null != loginName && "admin".equals(loginName)) {
            // 权限
            PermissionVo permissionVo1 = new PermissionVo("p1", "增加", "/admin/add/user");
            PermissionVo permissionVo2 = new PermissionVo("p2", "删除", "/admin/delete/user");
            PermissionVo permissionVo3 = new PermissionVo("p3", "修改", "/admin/update/user");
            PermissionVo permissionVo4 = new PermissionVo("p4", "查询", "/admin/get/user");
            List<PermissionVo> adminUserPermissions = new ArrayList<PermissionVo>();
            adminUserPermissions.add(permissionVo1);
            adminUserPermissions.add(permissionVo2);
            adminUserPermissions.add(permissionVo3);
            adminUserPermissions.add(permissionVo4);

            List<PermissionVo> normalUserPermissions = new ArrayList<PermissionVo>();
            normalUserPermissions.add(permissionVo4);
            //角色
            Role adminRole = new Role("r1", "adminRole", adminUserPermissions);
            Role normalRole = new Role("r2", "normalRole", normalUserPermissions);
            List<Role> userRoles = new ArrayList<Role>();
            userRoles.add(adminRole);
            userRoles.add(normalRole);
            //用户
            UserVo user = new UserVo("admin", "123456", userRoles);
            return user;
        } else {
            return null;
        }

    }

    @Override
    public UserVo getUserFromRedis(String loginName) {
        UserVo userVo =  getUserFromDataBase(loginName);
        return userVo;
    }


    @Override
    public SysUserToken getSysUserFromRedisByToken(String token) {
        //UserVo userVo =  getUserFromDataBase("admin");
        SysUserToken sysUserToken =  new SysUserToken();
        sysUserToken.setUserId("1");
        sysUserToken.setName("admin");
        sysUserToken.setToken("666666_ABCDEFG");
        return sysUserToken;
    }

    @Override
    public SysUserToken createToken(String userId) {
        // 生成一个token
        String token = UUID.randomUUID().toString().replace("-","");
        // 当前时间
        Date now = new Date();
        // 过期时间
        Date expireTime = new Date(now.getTime() + 30*60 * 1000);
        // 判断是否生成过token
        //SysUserToken sysUserToken = findByUserId(userId);
        SysUserToken sysUserToken = null;
        if(sysUserToken == null){
            sysUserToken = new SysUserToken();
            sysUserToken.setUserId(userId);
            sysUserToken.setToken(token);
            sysUserToken.setLastUpdateTime(now);
            sysUserToken.setExpireTime(expireTime);
            // 保存token，这里选择保存到数据库，也可以放到Redis或Session之类可存储的地方
            //save(sysUserToken);
        } else{
            sysUserToken.setToken(token);
            sysUserToken.setLastUpdateTime(now);
            sysUserToken.setExpireTime(expireTime);
            // 如果token已经生成，则更新token的过期时间
            //update(sysUserToken);
        }
        return sysUserToken;
    }
}
