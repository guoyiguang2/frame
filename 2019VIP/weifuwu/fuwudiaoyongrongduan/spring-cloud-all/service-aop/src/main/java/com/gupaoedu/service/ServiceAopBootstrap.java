package com.gupaoedu.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAutoConfiguration
@ComponentScan("com.gupaoedu.service.*")
//@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableAspectJAutoProxy
public class ServiceAopBootstrap {

    public static void main(String[] args) {
        SpringApplication.run(ServiceAopBootstrap.class, args);
    }
}
