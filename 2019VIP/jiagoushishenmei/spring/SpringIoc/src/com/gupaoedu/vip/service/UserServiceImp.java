package com.gupaoedu.vip.service;


import com.gupaoedu.vip.model.User;
import org.springframework.stereotype.Service;

public class UserServiceImp implements  UserService{

    public UserServiceImp() {
        System.out.println("静态工厂实例化 UserService ...");
    }

    @Override
    public User getUserByName(String name) {
        User user = new User();
        user.setName("慕容白");
        user.setSex("男");
        user.setAge("27");
        return user;
    }
}
