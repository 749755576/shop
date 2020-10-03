package com.zixi.shop.config.shiro;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro配置类
 */
@Configuration
public class ShiroConfig {
    /**
     *创建ShiroFilterFactoryBean
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();

        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //添加shiro内置过滤器  anon：无需认证登录可以访问   authc：必须认证才可以访问  user：如果使用rememberMe可以直接访问
        //perms：该资源必须得到资源权限才可以访问  role：必须得到角色权限才可以访问
        Map<String,String> fifterMap=new LinkedHashMap<>();
        fifterMap.put("/auth","anon");
        fifterMap.put("/index","anon");
        fifterMap.put("/*","authc");
        shiroFilterFactoryBean.setLoginUrl("/toLogin");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(fifterMap);
        return shiroFilterFactoryBean;
    }


    /**
     * cookie设置24小时
     * @return
     */
    @Bean
    public CookieRememberMeManager myRememberMeManager() {
        CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
        rememberMeManager.setCipherKey(Base64.decode("2AvVhdsgUs0FSA3SDFAdag=="));
        SimpleCookie simpleCookie = new SimpleCookie("remberMe");
        simpleCookie.setMaxAge(24 * 60 * 60 * 1000);
        rememberMeManager.setCookie(simpleCookie);
        return rememberMeManager;

    }

    /**
     * 设置这个为可以使用@RequiresPermission 来在方法上添加权限字符串
     * @return
     */
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }
    @Bean
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 创建SecurityManager
     * @return
     */
    @Bean(name = "securityManager")
    public SecurityManager securityManager(RememberMeManager rememberMeManager, UserRealm myShiroRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm);
        securityManager.setRememberMeManager(rememberMeManager);
        return securityManager;
    }
    //关联Realm
    @Bean(name = "userRealm")
    public UserRealm getRealm(){
        return new UserRealm();
    }
}
