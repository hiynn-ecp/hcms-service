package com.hiynn.cms.common.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * web应用静态资源路径配置类
 *
 * @author 张朋
 * @date 2019/11/14 15:32
 */
@Configuration
@Data
public class WebStaticLocationConfig {
    /**
     * 静态资源目录
     */
    @Value("${web.static-location.home}")
    private String home;

    /**
     * 图片目录
     */
    @Value("${web.static-location.img}")
    private String imgPath;

    /**
     * 其他文件目录
     */
    @Value("${web.static-location.files}")
    private String filesPath;

    /**
     * 数据源sql文件目录
     */
    @Value("${web.static-location.sql}")
    private String sqlPath;

    /**
     * 备份数据文件目录
     */
    @Value("${web.static-location.backups}")
    private String backupsPath;


}
