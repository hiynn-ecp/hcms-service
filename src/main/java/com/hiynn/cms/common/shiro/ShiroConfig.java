package com.hiynn.cms.common.shiro;

import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.CacheManager;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro配置
 * <p>
 * 注入开始级别 200
 *
 * @author shanming.yang
 * @date 2017-04-20 18:33
 */
@Configuration
@Slf4j
public class ShiroConfig {


    /**
     * spring 开启了 ehcache
     * <p>
     * 注入spring的CacheManager 到Shiro 的 EhCacheManager 中 使用
     * <p>
     * 如果需要自己配置缓存 指定 EhCacheManager.setCacheManagerConfigFile("classpath:xxx");
     * <p>
     * 不要注入使用spring 的 CacheManager
     *
     * @param cacheManager
     * @return org.apache.shiro.cache.ehcache.EhCacheManager
     * @author 张朋
     * @date 2019/11/5 14:56
     */
    @Bean
    @Order(200)
    public EhCacheManager ehCacheManager(CacheManager cacheManager) {
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManager(cacheManager);
        log.info("Spring注入缓存管理器到Shiro的EhCacheManager,管理器名称：{}", cacheManager.getName());
        return ehCacheManager;
    }


    @Bean
    @Order(201)
    public SessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new HCMSSessionManager();

        //设置session过期时间为1小时(单位：毫秒)，默认为30分钟
        sessionManager.setGlobalSessionTimeout(60 * 60 * 1000);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        sessionManager.setSessionDAO(new MemorySessionDAO());

        // 将JSESSIONID变成自定义名称 WEBJSESSIONID
        sessionManager.setSessionIdCookieEnabled(true);
        SimpleCookie cookie = new SimpleCookie("HIYNN-JSESSIONID");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60 * 60 * 1000);
        sessionManager.setSessionIdCookie(cookie);
        return sessionManager;
    }


    /***
     * shiro的在进行密码验证的时候，将会在此进行匹配
     * @return
     */
    @Bean()
    @Order(202)
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        // 设置散列算法
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        // 散列的次数，比如散列两次md5(md5(""))
        hashedCredentialsMatcher.setHashIterations(2);
        // 表示是否存储散列后的密码为16进制，需要和生成密码时的一样，默认是base64；
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        return hashedCredentialsMatcher;
    }

    // 跳过203 级别 在 ShiroRealm 中使用 因为 204 依赖于 203

    @Bean
    @Order(204)
    public SecurityManager securityManager(ShiroRealm shiroRealm, SessionManager sessionManager, EhCacheManager ehCacheManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm);
        securityManager.setSessionManager(sessionManager);
        securityManager.setCacheManager(ehCacheManager);
        return securityManager;
    }

   @Bean
   @Order(205)
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 前后端分离 未登录 跳转到 控制器来返回JSON
        shiroFilterFactoryBean.setLoginUrl("/sys/user/unauth");
        Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();
        // 注意这里不要用Bean的方式，否则会报错
        filters.put("authc", new ShiroUserFilter());
        shiroFilterFactoryBean.setFilters(filters);

        // 一定要采用链表Map 有序的特性 来配置
        Map<String, String> shiroFilterMap = new LinkedHashMap<>();
        // --- 跳过一些控制器API接口
        // 登录
        shiroFilterMap.put("/sys/user/login", "anon");
        // 退出登录
        shiroFilterMap.put("/sys/user/logout", "anon");
        // 首页
        shiroFilterMap.put("/", "anon");
        // ...

        // ---swagger配置
        shiroFilterMap.put("/swagger-ui.html", "anon");
        shiroFilterMap.put("/swagger-resources/**", "anon");
        shiroFilterMap.put("/v2/api-docs/**", "anon");
        shiroFilterMap.put("/webjars/springfox-swagger-ui/**", "anon");
        // ...

        // --- 静态文件资源
        shiroFilterMap.put("/img/**", "anon");
        shiroFilterMap.put("/files/**", "anon");
        shiroFilterMap.put("/jar/**", "anon");
        // ...

        // 以上之外的所有请求进行验证 注意配置顺序
        shiroFilterMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(shiroFilterMap);

        return shiroFilterFactoryBean;
    }

    /**
     * @return org.apache.shiro.spring.LifecycleBeanPostProcessor
     * @author 张朋
     * @date 2019/10/25 15:00
     */
    @Bean
    @Order(206)
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator和AuthorizationAttributeSourceAdvisor)即可实现此功能
     * <p>
     * DefaultAdvisorAutoProxyCreator是用来扫描上下文，寻找所有的Advistor(通知器）， 将这些Advisor应用到所有符合切入点的Bean中。
     * 所以必须在lifecycleBeanPostProcessor创建之后创建，所以用了 @DependsOn(value = "lifecycleBeanPostProcessor") 加载顺序使用了 Order排序
     * 所以不需要
     *
     * @return org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator
     * @DependsOn
     * @author 张朋
     * @date 2019/10/25 15:01
     */
    @Bean

    @Order(207)
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
        proxyCreator.setProxyTargetClass(true);
        return proxyCreator;
    }


    /**
     * 开启aop注解支持
     *
     * @param securityManager ''
     * @return org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor
     * @author 张朋
     * @date 2019/10/28 11:10
     */
    @Bean
    @Order(208)
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }


}
