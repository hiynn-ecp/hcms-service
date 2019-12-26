package com.hiynn.cms.dao;

import com.hiynn.cms.entity.SysRoleEntity;
import com.hiynn.cms.entity.SysUserRoleREntity;
import com.hiynn.component.common.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 角色表
 *
 * @author 张朋
 * @date 2019-10-24 17:19:53
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRoleEntity> {
    /**
     * 根据用户ID获取用户的角色
     *
     * @param userId 用户ID
     * @return java.util.List<com.hiynn.cms.entity.SysRoleEntity>
     * @author 张朋
     * @date 2019/10/28 14:06
     */
    List<SysRoleEntity> listByUserId(String userId);


    /**
     * 插入用户角色
     *
     * @param userRole
     * @return int
     * @author 张朋
     * @date 2019/11/4 17:35
     */
    int insertUserRole(SysUserRoleREntity userRole);

    /**
     * 清除用户的角色
     * @author 张朋
     * @date 2019/11/12 18:23
     * @param userId
     * @return int
     */
    int deleteUserRole(String userId);

}
