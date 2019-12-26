package com.hiynn.cms.service;

import com.github.pagehelper.PageInfo;
import com.hiynn.cms.entity.SysMenuEntity;
import com.hiynn.cms.model.dto.MenuDto;
import com.hiynn.cms.model.dto.RoleMenusDTO;
import com.hiynn.cms.model.vo.MenuVO;

import java.util.List;

/**
 * 目录/菜单/按钮/表
 *
 * @author 张朋
 * @date 2019-10-24 17:19:53
 */
public interface SysMenuService extends BaseService {

    /**
     * 获取当前用户按钮权限列表
     *
     * @return java.util.List<com.hiynn.cms.entity.SysMenuEntity>
     * @author 张朋
     * @date 2019/10/28 14:31
     */
    List<SysMenuEntity> listPermsByUser();


    /**
     * 获取当前登录用户的所有菜单/产品/按钮 树
     *
     * @return java.util.List<com.hiynn.cms.entity.SysMenuEntity>
     * @author 张朋
     * @date 2019/10/28 14:31
     */
    List<MenuVO> listTreeByUserId();

    /**
     * 获取所有菜单/产品/按钮 树
     *
     * @return java.util.List<com.hiynn.cms.entity.SysMenuEntity>
     * @author 张朋
     * @date 2019/10/28 14:31
     */
    List<MenuVO> listTree();

    /**
     * 获取某个角色所有菜单/产品/按钮 树；
     *
     * @param roleId
     * @return java.util.List<com.hiynn.cms.model.vo.MenuVO>
     * @author 张朋
     * @date 2019/11/4 18:16
     */
    List<MenuVO> listTreeByRoleId(String roleId);

    /**
     * ID查询
     */
    SysMenuEntity select(String id);

    /**
     * 分页查询
     */
    PageInfo<SysMenuEntity> listPage(Integer page, Integer pageSize);

    /**
     * 查询总数
     */
    int countTotal();

    /**
     * 保存
     */
    int insert(MenuDto menuDto);


    /**
     * 给指定角色插入一组菜单
     *
     * @param roleMenus
     * @return int
     * @author 张朋
     * @date 2019/11/5 15:03
     */
    int insertRoleMenus(RoleMenusDTO roleMenus);


    /**
     * 更新
     */
    int update(MenuDto menuDto);

    /**
     * ID删除
     */
    int delete(String id);
}


