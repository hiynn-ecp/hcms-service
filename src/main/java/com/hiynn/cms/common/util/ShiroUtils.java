package com.hiynn.cms.common.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * shiro 常用工具
 *
 * @author 张朋
 * @date 2019/10/30 14:00
 */
@Slf4j
public class ShiroUtils {


    /**
     * 获取当前登录的上下文
     *
     * @return org.apache.shiro.subject.Subject
     * @author 张朋
     * @date 2019/10/30 15:56
     */
    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    /**
     * 通用型 获取用户
     *
     * @param clazz
     * @return T
     * @author 张朋
     * @date 2019/10/30 15:56
     */
    public static <T> T getUser(Class<T> clazz) {
        // 获取登录验证函数doGetAuthenticationInfo 中我们放入SimpleAuthenticationInfo的第一个参数
        Object principal = getSubject().getPrincipal();
        if (principal == null) {
            log.error("获取当前用户失败");
            throw new RuntimeException("获取当前用户失败");
        }
        return clazz.cast(principal);
    }

}
