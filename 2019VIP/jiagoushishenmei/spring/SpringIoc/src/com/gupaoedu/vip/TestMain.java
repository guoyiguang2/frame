package com.gupaoedu.vip;

import com.gupaoedu.vip.model.Person;
import com.gupaoedu.vip.model.User;
import com.gupaoedu.vip.service.UserService;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.InstantiationStrategy;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.Constructor;

public class TestMain {
    public static void main(String[] args) {
        //查询类路径 加载配置文件
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-config.xml");
        //根据id获取bean
        //Spring就是一个大工厂（容器）专门生成bean bean就是对象
        //Person person = (Person) applicationContext.getBean("person");
        //输出获取到的对象
        //System.out.println("person = " + person);


        //静态工厂实例化
//        UserService userService = (UserService) applicationContext.getBean("userStaticService");
//        System.out.println("userService = " + userService);
//        User user = (User)userService.getUserByName("李少白");
//        System.out.println("user = " + user);
        //实例工厂
//        UserService userService2 = applicationContext.getBean(UserService.class);
//        System.out.println("userService = " + userService2);

    }
}
