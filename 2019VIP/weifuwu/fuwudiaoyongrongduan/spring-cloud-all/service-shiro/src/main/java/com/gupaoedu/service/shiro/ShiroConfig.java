package com.gupaoedu.service.shiro;


import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    /**
          * 将自己的验证方法注入容器
          * @prama
          * @return
          * @date 2018/12/5 15:28
          */
    @Bean
    public MyShiroRealm myShiroRealm(){
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        return myShiroRealm;
    }

    /**
      * 权限管理,配置主要的realm的管理认证
      * @prama
      * @return
      * @date 2018/12/5 15:30
      */
    @Bean
    public SecurityManager securityManager() {
    DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
    // 设置realm.
    securityManager.setRealm(myShiroRealm());
    return securityManager;
}

   /**
      * Filter工厂，设置对应的过滤条件和跳转条件
      * @prama
      * @return
      * @date 2018/12/5 16:02
      */
     @Bean
     public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
         ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
    // 必须设置 SecurityManager
    shiroFilterFactoryBean.setSecurityManager(securityManager);
     // 登录
    shiroFilterFactoryBean.setLoginUrl("/notLogin");
    // 设置无权限时跳转的 ur
    shiroFilterFactoryBean.setUnauthorizedUrl("/notRole");
         // 自定义 OAuth2Filter 过滤器，替代默认的过滤器
         Map<String, Filter> filters = new HashMap<>();
         filters.put("oauth2", oAuth2FilterBean());
         shiroFilterFactoryBean.setFilters(filters);
    // 设置拦截器
     Map<String,String> map = new LinkedHashMap<String, String>();

    // 普通用户
     map.put("/user/**", "roles[user]");
     // admin
     //map.put("/admin/**", "roles[admin]");
     // 开放登陆接口 问路径拦截配置，"anon"表示无需验证，未登录也可访问
     //map.put("/login", "anon");
     // 登出
    //        map.put("/logout","logout");
    // 对所有用户认证
     map.put("/**","authc");
     // 其他所有路径交给OAuth2Filter处理
     map.put("/**", "oauth2");
    shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
     System.out.println("Shiro拦截器工厂类注入成功");
    return shiroFilterFactoryBean;
     }

     @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
     AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
     authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
     return authorizationAttributeSourceAdvisor;
     }


    /**
     * 可以在 过滤器中使用bean
     * 因为项目应用了SpringBoot，所以我们项目启动时，先初始化listener，因此注解的bean会被初始化和注入；
     * 然后再来就filter的初始化，再接着才到我们的dispathServlet的初始化，因此，当我们需要在filter里注入一个注解的bean时，
     * 就会注入失败，因为filter初始化时，注解的bean还没初始化，没法注入。
     * 将 过滤器交给spring管理，过滤器中就可以用 @Autowired 了（否则是 Autowired 的对象是 空的）
     */
    @Bean
    public Filter oAuth2FilterBean() {
        return  new OAuth2Filter();
    }
}
