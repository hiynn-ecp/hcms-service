package com.hiynn.cms.common.util;

import lombok.extern.slf4j.Slf4j;

/**
 * 实体类工具类
 *
 * @author 张朋
 * @date 2019/10/30 15:30
 */
@Slf4j
public class BeanUtils {

    public static <T> T copy(Object source, Class<T> target) {
        T t;
        try {
            t = target.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("数据拷贝异常:", e);
            throw new RuntimeException(e);
        }
        org.springframework.beans.BeanUtils.copyProperties(source, t);
        return t;
    }


}
