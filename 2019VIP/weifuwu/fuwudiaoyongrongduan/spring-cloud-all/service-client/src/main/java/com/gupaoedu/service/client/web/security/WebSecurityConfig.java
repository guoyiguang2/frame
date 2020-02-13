package com.gupaoedu.service.client.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.annotation.PostConstruct;
import java.util.List;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private List<IWebSecurityConfigurer> configurers;

    @PostConstruct
    public void init() {
        AnnotationAwareOrderComparator.sort(configurers);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        configurers.forEach(c -> {
            try {
                c.configure(web);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        configurers.forEach(c -> {
            try {
                c.configure(http);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


}
