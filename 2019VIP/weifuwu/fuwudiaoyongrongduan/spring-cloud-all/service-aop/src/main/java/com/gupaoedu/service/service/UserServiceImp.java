package com.gupaoedu.service.service;

import com.gupaoedu.service.entity.PermissionVo;
import com.gupaoedu.service.entity.Role;
import com.gupaoedu.service.entity.UserVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserServiceImp implements UserService {

    @Override
    public UserVo getUserFromDataBase(String loginName) {
        if (null != loginName && "admin".equals(loginName)) {
            // 权限
            PermissionVo permissionVo1 = new PermissionVo("p1", "增加", "add");
            PermissionVo permissionVo2 = new PermissionVo("p2", "删除", "delete");
            PermissionVo permissionVo3 = new PermissionVo("p3", "修改", "update");
            PermissionVo permissionVo4 = new PermissionVo("p4", "查询", "get");
            List<PermissionVo> userPermissions = new ArrayList<PermissionVo>();
            userPermissions.add(permissionVo1);
            userPermissions.add(permissionVo2);
            userPermissions.add(permissionVo3);
            userPermissions.add(permissionVo4);
            //角色
            Role userRole = new Role("r1", "用户角色r1", userPermissions);
            List<Role> userRoles = new ArrayList<Role>();
            userRoles.add(userRole);
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
    public UserVo getUserFromRedisByToken(String token) {
        UserVo userVo =  getUserFromDataBase("admin");
        return userVo;
    }
}
