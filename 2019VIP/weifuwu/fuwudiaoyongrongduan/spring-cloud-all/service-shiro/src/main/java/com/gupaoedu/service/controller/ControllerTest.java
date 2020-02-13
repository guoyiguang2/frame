package com.gupaoedu.service.controller;

import com.gupaoedu.service.annotation.Limited;
import com.gupaoedu.service.annotation.Timeout;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@RestController
public class ControllerTest {

    // 外部化配置其实是有点不靠谱的 - 它并非完全静态，也不一定及时返回

    private final Environment environment;

    public ControllerTest(Environment environment) {
        this.environment = environment;
    }

    private String getPort() {
        return environment.getProperty("local.server.port");
    }


    @GetMapping("/admin/login")
    @ResponseBody
    public Map login(String loginName , String password) {
        //生成token
        String token = UUID.randomUUID().toString().replace("-","");
        Map result =   new HashMap<>();
        result.put("token",token);
        result.put("name",loginName);
        result.put("password",password);
        // token 为key，user（包括用户输入的 用户名和密码进行加密后存入redis）对象为值获取用户信息

        return result;
    }


    @GetMapping("/notLogin")
    public String notLogin(String loginName ,String password) {
        return "Failed  "+loginName+"    "+password;
    }

    @GetMapping("/notRole")
    public String notRole(String loginName ,String password) {
        return "NoRole  "+loginName+"    "+password;
    }

    @GetMapping("/user")
    public String user(String loginName ,String password) {
        return "User  "+loginName+"    "+password;
    }



    @GetMapping("/admin")
    public String admin(String loginName ,String password) {
        return "Admin  "+loginName+"    "+password;
    }

    @GetMapping("/admin/add")
    @RequiresPermissions("/admin/add/user")
    public String adminAdd(String loginName ,String password) {
        return "adminAdd  "+loginName+"    "+password;
    }

    @GetMapping("/admin/delete")
    @RequiresPermissions("/admin/delete/user")
    public String adminDelete(String loginName ,String password) {
        return "adminDelete  "+loginName+"    "+password;
    }

    @GetMapping("/admin/update")
    @RequiresPermissions("/admin/update/user")
    public String adminUpdate(String loginName ,String password) {
        return "adminUpdate  "+loginName+"    "+password;
    }

    @GetMapping("/admin/get")
    @RequiresPermissions("/admin/get/user")
    public String adminGet(String name ,String password) {
        return "adminGet  "+name+"    "+password;
    }

    @GetMapping("/admin/getNoPermission")
    @RequiresPermissions("/no")
    public String getNoPermission(String name ,String password) {
        return "getNoPermission  "+name+"    "+password;
    }


    @GetMapping("/admin/getNormalRole")
    @RequiresRoles("/normalRole")
    public String getNormalRole(String name ,String password) {
        return "getNormalRole  "+name+"    "+password;
    }


    @GetMapping("/admin/getAdminRole")
    @RequiresRoles("/adminRole")
    public String getAdminRole(String name ,String password) {
        return "getAdminRole  "+name+"    "+password;
    }

    @GetMapping("/admin/getSuperRole")
    @RequiresRoles("/superRole")
    public String getSuperRole(String name ,String password) {
        return "getSuperRole  "+name+"    "+password;
    }



    @GetMapping(value = "/shiro/{message}")
    public String shiro(@PathVariable String message) {
        return "[SHIRO:" + getPort() + "] " + message;
    }






}
