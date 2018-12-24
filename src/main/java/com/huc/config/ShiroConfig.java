package com.huc.config;

import com.huc.security.SecurityRealm;
import com.huc.service.base.UserService;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.ExecutorServiceSessionValidationScheduler;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.SessionValidationScheduler;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

public class ShiroConfig {
    @Autowired
    private UserService userService;
    /**
     * 声明自定义的realm
     * @return
     */
    @Bean
    public SecurityRealm securityRealm(){
        SecurityRealm realm = new SecurityRealm();
        realm.setUserService(userService);
        return realm;
    }

    /**
     * 配置会话管理器
     * @return
     */
    @Bean
    public SessionManager sessionManager(){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        //设置session的过期时间，默认是30分钟(毫秒)
        sessionManager.setGlobalSessionTimeout(600000);
        //删除失效的session
        sessionManager.setDeleteInvalidSessions(true);
        //是否开启会话验证器
        sessionManager.setSessionValidationScheduler(sessionValidationScheduler());
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        //设置cookie
        Cookie cookie = new SimpleCookie();
        cookie.setHttpOnly(false);
        //cookie 的过期时间
        cookie.setMaxAge(300);
        cookie.setName("PMS_COOKIE");
        sessionManager.setSessionIdCookie(cookie);
        //在操作时启动
        sessionManager.setSessionIdCookieEnabled(true);
        return sessionManager;
    }

    /**
     * 定义会话清理调度器
     * @return
     */
    @Bean
    public SessionValidationScheduler sessionValidationScheduler(){
        ExecutorServiceSessionValidationScheduler scheduler = new ExecutorServiceSessionValidationScheduler();
        scheduler.setInterval(10);
        return scheduler;
    }
    /**
     * 声明认证管理器
     * @return
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(securityRealm());
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    /**
     * 声明过滤工厂类
     * @return
     */
    @Bean()
    public ShiroFilterFactoryBean shiroFilter(){
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager());
//        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String,String>();
//        filterChainDefinitionMap.put("/static/**", "anon");
//        filterChainDefinitionMap.put("/mvc/login", "anon");
//        filterChainDefinitionMap.put("/**", "authc");
//        factoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
//        factoryBean.setLoginUrl("/login");
        return factoryBean;
    }
    @Bean
    public  LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }
}
