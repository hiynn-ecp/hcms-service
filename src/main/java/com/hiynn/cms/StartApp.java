package com.hiynn.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 程序入口
 * <p>
 * 开启缓存管理 @EnableCaching 默认扫描ehcache.xml 可在配置中配置为其他名称 避开与其他框架的冲突
 * <p>
 * 其他框架也可以和spring 共用一个缓存管理器
 * <p>
 * 继承 SpringBootServletInitializer 重写configure
 * <p>
 * 解决war包在容器中启动无法初始化Servlet入口的问题
 *
 * @author 张朋
 * @date 2019/10/21 15:27
 */
@SpringBootApplication
@EnableCaching
public class StartApp extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(StartApp.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(StartApp.class, args);
    }

}
