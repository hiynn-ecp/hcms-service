package com.hiynn.cms.common.cross;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * 跨域配置
 * @author 张朋
 * @date 2019/11/1 15:09
 */
@Configuration
public class CrossConfig {

    /**
     * 跨域过滤器 优先级高
     *
     * @return
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public FilterRegistrationBean crossFilterRegistration() {
        FilterRegistrationBean<CrossOriginFilter> registration = new FilterRegistrationBean<>(new CrossOriginFilter());
        registration.addUrlPatterns("/*");
        registration.setName("crossFilter");
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registration;
    }
}
