package com.gupaoedu.vip.controller;

import com.gupaoedu.vip.model.User;
import com.gupaoedu.vip.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {


    @Autowired
    public  UserService  userService;

    @GetMapping("/getUserByName")
    public User getUserByName(String  name) {
        User user =  userService.getUserByName(name);
        return user;
    }


}
