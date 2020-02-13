package com.gupaoedu.service.client.web.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@EnableWebSecurity // Spring Boot 场景不需要重新标注
@Configuration
@Order
public class CsrfWebSecurityConfig implements WebMvcConfigurer, IWebSecurityConfigurer {

    @Bean
    public CsrfTokenRepository csrfTokenRepository() {
        return new HttpSessionCsrfTokenRepository();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .requireCsrfProtectionMatcher(request -> "POST".equalsIgnoreCase(request.getMethod())) // 配置所保护资源请求
                .csrfTokenRepository(csrfTokenRepository()) // 替换成 HttpSession 的方式保存
        ;
    }
}
