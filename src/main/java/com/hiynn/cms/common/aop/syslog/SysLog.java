package com.hiynn.cms.common.aop.syslog;

import javax.validation.constraints.Null;
import java.lang.annotation.*;

/**
 * 系统日志AOP扫描注解
 *
 * @author zhang
 * @date 2019-07-25
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

    /**
     * 产品描述
     * @return
     */

    String product() default "";
    /**
     * 操作描述
     * @return
     */
    @Null
    String operation() default "";

}
