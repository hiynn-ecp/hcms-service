package com.hiynn.cms.dao;

import com.hiynn.cms.entity.SysUserEntity;
import com.hiynn.component.common.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户表
 *
 * @author 张朋
 * @date 2019-10-24 17:19:52
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUserEntity> {
    /**
     * 根据账户获取用户信息
     *
     * @param account 用户账号
     * @return com.hiynn.cms.entity.SysUserEntity
     * @author 张朋
     * @date 2019/10/28 14:05
     */
    SysUserEntity selectByAccount(String account);
}
