package com.hiynn.cms.service;

import com.github.pagehelper.PageInfo;
import com.hiynn.cms.entity.SysRoleEntity;
import com.hiynn.cms.model.dto.RoleDTO;
import com.hiynn.cms.model.dto.UserRolesDTO;

import java.util.List;

/**
 * 角色表
 *
 * @author 张朋
 * @date 2019-10-24 17:19:53
 */
public interface SysRoleService extends BaseService {

    /**
     * 获取用户角色
     *
     * @param userId
     * @return java.util.List<com.hiynn.cms.service.SysRoleService>
     * @author 张朋
     * @date 2019/10/28 14:26
     */
    List<SysRoleEntity> listByUserId(String userId);


    /**
     * 给指定用户设置一组角色
     *
     * @param userRolesDTO
     * @return int
     * @author 张朋
     * @date 2019/11/4 18:04
     */
    int insertUserRoles(UserRolesDTO userRolesDTO);


    /**
     * ID查询
     */
    SysRoleEntity select(String id);

    /**
     * 分页查询
     */
    PageInfo<SysRoleEntity> listPage(Integer page, Integer pageSize);

    /**
     * 查询总数
     */
    int countTotal();

    /**
     * 保存
     */
    int insert(RoleDTO role);

    /**
     * 更新
     */
    int update(RoleDTO role);

    /**
     * ID删除
     */
    int delete(String id);

}
