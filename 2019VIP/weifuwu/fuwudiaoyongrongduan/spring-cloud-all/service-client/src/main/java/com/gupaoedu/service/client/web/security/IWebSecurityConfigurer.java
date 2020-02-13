package com.gupaoedu.service.client.web.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;

public interface IWebSecurityConfigurer {

    default void configure(HttpSecurity http) throws Exception {
        // DO NOTHING
    }

    default void configure(WebSecurity http) throws Exception {
        // DO NOTHING
    }
}
