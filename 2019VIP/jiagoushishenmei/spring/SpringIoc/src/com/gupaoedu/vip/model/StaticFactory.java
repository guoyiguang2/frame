package com.gupaoedu.vip.model;

import com.gupaoedu.vip.service.UserService;
import com.gupaoedu.vip.service.UserServiceImp;

public class StaticFactory {

    //通过静态工厂的方式来实例化对象
    private static UserService userService = new UserServiceImp();

    public static UserService getInstance() {
        return userService;
    }
}
