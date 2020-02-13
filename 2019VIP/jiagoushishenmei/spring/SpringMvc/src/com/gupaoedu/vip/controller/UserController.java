package com.gupaoedu.vip.controller;

import com.gupaoedu.vip.model.User;
import com.gupaoedu.vip.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class UserController {

    @Autowired
    public  UserService  userService;

    @RequestMapping("/getUserByName")
    public String getUserByName() {
        User user =   userService.getUserByName("");
        System.out.println(user);
        return "success";
    }

}
