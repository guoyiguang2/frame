package com.gupaoedu.vip.model;

import com.gupaoedu.vip.service.UserService;
import com.gupaoedu.vip.service.UserServiceImp;

//实例工厂类
public class InstanceFactory {

    private UserService userService = new UserServiceImp();

    public UserService getInstance(){
        return userService;
    }
}
