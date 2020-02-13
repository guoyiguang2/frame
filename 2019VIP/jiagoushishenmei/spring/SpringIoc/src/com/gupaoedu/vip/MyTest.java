package com.gupaoedu.vip;


import com.gupaoedu.vip.model.User;
import com.gupaoedu.vip.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//Spring单元测试
//使用ContextConfiguration加载配置文件,使用@RunWith进行单元测试
@ContextConfiguration(locations = {"classpath*:spring-config.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class MyTest {

    @Autowired
    UserService userService;




    @Test
    public void getUser() {
      User user =  userService.getUserByName("libai");

        System.out.println(user);
    }



}
