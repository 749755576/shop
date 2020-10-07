package com.zixi.shop.config.shiro;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
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

import javax.servlet.Filter;
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
        fifterMap.put("/*","authc");
        fifterMap.put("/**","corsAuthenticationFilter");
        shiroFilterFactoryBean.setLoginUrl("/toLogin");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(fifterMap);
        //自定义过滤器
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        filterMap.put("corsAuthenticationFilter", corsAuthenticationFilter());
        shiroFilterFactoryBean.setFilters(filterMap);
        return shiroFilterFactoryBean;
    }


    /**
     * cookie对象;
     * rememberMeCookie()方法是设置Cookie的生成模版，比如cookie的name，cookie的有效时间等等。
     * @return
     */
    @Bean
    public SimpleCookie rememberMeCookie(){
        //System.out.println("ShiroConfiguration.rememberMeCookie()");
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //<!-- 记住我cookie生效时间30天 ,单位秒;-->
        simpleCookie.setMaxAge(259200);
        return simpleCookie;
    }

    /**
     * cookie管理对象;
     * rememberMeManager()方法是生成rememberMe管理器，而且要将这个rememberMe管理器设置到securityManager中
     * @return
     */
    @Bean
    public CookieRememberMeManager rememberMeManager(){
        //System.out.println("ShiroConfiguration.rememberMeManager()");
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
        cookieRememberMeManager.setCipherKey(Base64.decode("2AvVhdsgUs0FSA3SDFAdag=="));
        return cookieRememberMeManager;
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
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    //自定义sessionManager
    @Bean
    public SessionManager sessionManager() {
        return new CustomSessionManager();
    }

    public CORSAuthenticationFilter corsAuthenticationFilter(){
        return new CORSAuthenticationFilter();
    }
    //关联Realm
    @Bean(name = "userRealm")
    public UserRealm getRealm(){
        return new UserRealm();
    }
}
