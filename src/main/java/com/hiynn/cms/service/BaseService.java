package com.hiynn.cms.service;

import com.hiynn.cms.common.util.ShiroUtils;
import com.hiynn.cms.entity.SysUserEntity;

/**
 * @author 张朋
 * @date 2019/11/11 11:03
 */
interface BaseService {


    /**
     * 获取当前登录用户
     *
     * @return com.hiynn.cms.entity.SysUserEntity
     * @author 张朋
     * @date 2019/11/11 11:06
     */
    default SysUserEntity getUser() {
        return ShiroUtils.getUser(SysUserEntity.class);
    }

    /**
     * 获取当前用户id
     *
     * @return java.lang.String
     * @author 张朋
     * @date 2019/11/11 11:07
     */
    default String getUserId() {
        SysUserEntity user = getUser();
        return user == null ? null : user.getId();
    }


}
