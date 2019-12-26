package com.hiynn.cms.dao;

import com.hiynn.cms.entity.SysMenuEntity;
import com.hiynn.cms.entity.SysRoleMenuREntity;
import com.hiynn.component.common.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 目录/菜单/按钮/表
 *
 * @author 张朋
 * @date 2019-10-24 17:19:53
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenuEntity> {

    /**
     * 根据多个角色id 获取 所有权限
     *
     * @param userId 用户id
     * @return List<String>
     * @author 张朋
     * @date 2019/10/28 14:13
     */
    List<SysMenuEntity> listPermsByUserId(String userId);

    /**
     * 根据用户ID 获取用户的所有菜单/产品/按钮权限 列表
     *
     * @param userId
     * @return java.util.List<com.hiynn.cms.entity.SysMenuEntity>
     * @author 张朋
     * @date 2019/10/28 14:31
     */
    List<SysMenuEntity> listByUserId(String userId);

    /**
     * 插入角色菜单
     *
     * @param roleMenu
     * @return int
     * @author 张朋
     * @date 2019/11/4 17:35
     */
    int insertRoleMenu(SysRoleMenuREntity roleMenu);




    /**
     * 获取所有菜单/产品/按钮 树
     *
     * @return java.util.List<com.hiynn.cms.entity.SysMenuEntity>
     * @author 张朋
     * @date 2019/10/28 14:31
     */
    List<SysMenuEntity> listTree();


    /**
     *获取某个角色所有菜单/产品/按钮 树；
     * @author 张朋
     * @date 2019/11/4 18:15
     * @param roleId
     * @return java.util.List<com.hiynn.cms.entity.SysMenuEntity>
     */
    List<SysMenuEntity> listTreeByRoleId(String roleId);

    /**
     * 清除角色的菜单，分配前调用
     * @param roleId
     * @return
     */
    int deleteRoleMenu(String roleId);

}
