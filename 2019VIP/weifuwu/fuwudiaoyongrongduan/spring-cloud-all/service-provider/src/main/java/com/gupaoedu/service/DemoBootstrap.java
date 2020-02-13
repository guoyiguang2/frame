package com.gupaoedu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

import java.lang.annotation.*;
import java.util.Collections;
import java.util.Map;

@EnableAutoConfiguration
public class DemoBootstrap {

    @Autowired
    private Map<String, String> allStringBeans = Collections.emptyMap();

    // 通过 @Qualifier value() 属性来依赖查找
    @Autowired
    @Qualifier("a")
    private String aBean;

    @Autowired
    @Qualifier("b")
    private String bBean;

    @Autowired
    @Qualifier("c")
    private String cBean;

    // 不通过 @Qualifier value() 属性来依赖查找
    @Autowired
//    @Qualifier
    @Group
    private Map<String, String> groupStringBeans = Collections.emptyMap();


    @Bean
    public ApplicationRunner runner() {
        return args -> {
            System.out.println("allStringBeans : " + allStringBeans);

            System.out.println("aBean : "+aBean);
            System.out.println("bBean : "+bBean);
            System.out.println("cBean : "+cBean);

            System.out.println("groupStringBeans : " + groupStringBeans);
        };
    }

    @Bean
    public String a() {
        return "string-a";
    }

    // b and c 分组 -> @Qualifier
    @Bean
//    @Qualifier
    @Group
    public String b() {
        return "string-b";
    }

    @Bean
//    @Qualifier
    @Group
    public String c() {
        return "string-c";
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(DemoBootstrap.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }
}

// 自定义注解 - 元注解 @Qualifier
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Qualifier
@interface Group {

}
