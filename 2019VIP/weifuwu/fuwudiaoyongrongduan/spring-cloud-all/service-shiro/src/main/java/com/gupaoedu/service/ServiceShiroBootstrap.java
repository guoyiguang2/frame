package com.gupaoedu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@EnableAutoConfiguration
@ComponentScan
//@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableAspectJAutoProxy
public class ServiceShiroBootstrap {

    public static void main(String[] args) {
        SpringApplication.run(ServiceShiroBootstrap.class, args);
    }
}
