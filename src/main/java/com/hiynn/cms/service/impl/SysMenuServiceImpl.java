package com.hiynn.cms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hiynn.cms.common.util.BeanUtils;
import com.hiynn.cms.common.util.IDUtils;
import com.hiynn.cms.dao.SysMenuMapper;
import com.hiynn.cms.entity.SysMenuEntity;
import com.hiynn.cms.entity.SysRoleMenuREntity;
import com.hiynn.cms.model.dto.MenuDto;
import com.hiynn.cms.model.dto.RoleMenusDTO;
import com.hiynn.cms.model.vo.MenuVO;
import com.hiynn.cms.model.vo.PageData;
import com.hiynn.cms.service.SysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 目录/菜单/按钮/表
 *
 * @author 张朋
 * @date 2019-10-24 17:19:53
 */
@Service
@Slf4j
public class SysMenuServiceImpl implements SysMenuService {

    private final SysMenuMapper sysMenuMapper;

    @Autowired
    public SysMenuServiceImpl(SysMenuMapper sysMenuMapper) {
        this.sysMenuMapper = sysMenuMapper;
    }

    /**
     * 获取当前用户按钮权限列表
     *
     * @return java.util.List<com.hiynn.cms.entity.SysMenuEntity>
     * @author 张朋
     * @date 2019/10/28 14:31
     */
    @Override
    public List<SysMenuEntity> listPermsByUser() {
        return sysMenuMapper.listPermsByUserId(this.getUserId());
    }

    /**
     * 根据用户ID 获取用户的所有菜单/产品/按钮 树菜单
     *
     * @return java.util.List<com.hiynn.cms.entity.SysMenuEntity>
     * @author 张朋
     * @date 2019/10/28 14:31
     */
    @Override
    public List<MenuVO> listTreeByUserId() {

        List<SysMenuEntity> menus = sysMenuMapper.listByUserId(this.getUserId());

        // 转换包装类
        List<MenuVO> menuALL = new ArrayList<>();
        for (SysMenuEntity menu : menus) {
            menuALL.add(BeanUtils.copy(menu, MenuVO.class));
        }
        // 根节点
        List<MenuVO> rootMenu = new ArrayList<MenuVO>();
        for (MenuVO menu : menuALL) {
            // 父ID不存在，为根节点。
            if (StringUtils.isBlank(menu.getMenuId())) {
                rootMenu.add(menu);
            }
        }
        //为根菜单设置子菜单，getClild是递归调用的
        for (MenuVO menu : rootMenu) {
            // 获取根节点下的所有子节点
            List<MenuVO> childList = getChild(menu.getId(), menuALL);
            // 设置子菜单/按钮等
            menu.setChild(childList);
        }
        return rootMenu;
    }

    /**
     * 获取所有菜单/产品/按钮 树
     *
     * @return java.util.List<com.hiynn.cms.entity.SysMenuEntity>
     * @author 张朋
     * @date 2019/10/28 14:31
     */
    @Override
    public List<MenuVO> listTree() {

        List<SysMenuEntity> menus = sysMenuMapper.listTree();

        // 转换包装类
        List<MenuVO> menuALL = new ArrayList<>();
        for (SysMenuEntity menu : menus) {
            menuALL.add(BeanUtils.copy(menu, MenuVO.class));
        }

        // 根节点
        List<MenuVO> rootMenu = new ArrayList<MenuVO>();
        for (MenuVO menu : menuALL) {
            // 父ID不存在，为根节点。
            if (StringUtils.isBlank(menu.getMenuId())) {
                rootMenu.add(menu);
            }
        }
        //为根菜单设置子菜单，getClild是递归调用的
        for (MenuVO menu : rootMenu) {
            // 获取根节点下的所有子节点
            List<MenuVO> childList = getChild(menu.getId(), menuALL);
            // 设置子菜单/按钮等
            menu.setChild(childList);
        }
        return rootMenu;
    }

    /**
     * 获取某个角色所有菜单/产品/按钮 树；
     *
     * @param roleId
     * @return java.util.List<com.hiynn.cms.entity.SysMenuEntity>
     * @author 张朋
     * @date 2019/10/28 14:31
     */
    @Override
    public List<MenuVO> listTreeByRoleId(String roleId) {
        List<SysMenuEntity> menus = sysMenuMapper.listTreeByRoleId(roleId);

        // 转换包装类
        List<MenuVO> menuALL = new ArrayList<>();
        for (SysMenuEntity menu : menus) {
            menuALL.add(BeanUtils.copy(menu, MenuVO.class));
        }

        // 根节点
        List<MenuVO> rootMenu = new ArrayList<MenuVO>();
        for (MenuVO menu : menuALL) {
            // 父ID不存在，为根节点。
            if (StringUtils.isBlank(menu.getMenuId())) {
                rootMenu.add(menu);
            }
        }
        //为根菜单设置子菜单，getClild是递归调用的
        for (MenuVO menu : rootMenu) {
            // 获取根节点下的所有子节点
            List<MenuVO> childList = getChild(menu.getId(), menuALL);
            // 设置子菜单/按钮等
            menu.setChild(childList);
        }
        return rootMenu;
    }


    /**
     * 菜单递归函数
     *
     * @param id
     * @param menuALL
     * @return java.util.List<com.hiynn.cms.model.vo.MenuVO>
     * @author 张朋
     * @date 2019/10/31 16:11
     */
    private List<MenuVO> getChild(String id, List<MenuVO> menuALL) {
        //子菜单
        List<MenuVO> childList = new ArrayList<>();
        for (MenuVO menu : menuALL) {
            // 遍历所有节点，将所有菜单的父id与传过来的根节点的id比较
            //相等说明：为该根节点的子节点。
            if (menu.getMenuId().equals(id)) {
                childList.add(menu);
            }
        }
        //递归
        for (MenuVO menu : childList) {
            menu.setChild(getChild(menu.getId(), menuALL));
        }
        //如果节点下没有子节点，返回一个空List（递归退出）
        if (childList.size() == 0) {
            return new ArrayList<>();
        }
        return childList;
    }


    @Override
    public SysMenuEntity select(String id) {
        return sysMenuMapper.select(id);
    }

    @Override
    public PageInfo<SysMenuEntity> listPage(Integer page, Integer pageSize) {
        // 配置分页参数
        PageHelper.startPage(page, pageSize);
        // 查询菜单
        List<SysMenuEntity> listPage = sysMenuMapper.list();

        // 返回的pageInfo 对象 使用PageData 重写 排除无用字段
        PageInfo<SysMenuEntity> pageInfo = new PageData<>(listPage);

        // 转换pagedata中返回模型 排除模型无用字段
        List<SysMenuEntity> menus = new ArrayList<>();
        for (SysMenuEntity menu : pageInfo.getList()) {
            menus.add(BeanUtils.copy(menu, MenuVO.class));
        }
        pageInfo.setList(menus);

        return pageInfo;
    }

    @Override
    public int countTotal() {
        return sysMenuMapper.countTotal();
    }

    @Override
    public int insert(MenuDto menuDto) {
        SysMenuEntity sysMenu = BeanUtils.copy(menuDto, SysMenuEntity.class).insert(this.getUserId());
        return sysMenuMapper.insert(sysMenu);
    }

    /**
     * 给指定角色插入一组菜单
     * <p>
     * 如果不传入菜单集合属清空操作
     *
     * @param roleMenus
     * @return int
     * @author 张朋
     * @date 2019/11/5 15:03
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int insertRoleMenus(RoleMenusDTO roleMenus) {
        Date nowDate = new Date();
        // 角色id
        String roleId = roleMenus.getRoleId();
        //先清除原来的关系
        sysMenuMapper.deleteRoleMenu(roleId);

        Set<String> menuIds = roleMenus.getMenuIds();
        if (menuIds != null) {
            // 遍历所有菜单 并入库 报错就回滚  否则返回成功
            for (String menuId : menuIds) {
                SysRoleMenuREntity roleMenu = new SysRoleMenuREntity();
                roleMenu.setRoleId(roleId);
                roleMenu.setCreateTime(nowDate);
                roleMenu.setMenuId(menuId);
                roleMenu.setId(IDUtils.getJavaUUID());
                sysMenuMapper.insertRoleMenu(roleMenu);
            }
        }
        // 入库 理论上校验过参数后就不会出错 如果一旦出错 就是数据库和服务端的问题 直接捕获全局异常返回即可
        return 1;
    }

    @Override
    public int update(MenuDto menuDto) {
        SysMenuEntity entity = BeanUtils.copy(menuDto, SysMenuEntity.class).update(this.getUserId());
        return sysMenuMapper.update(entity);
    }

    @Override
    public int delete(String id) {
        SysMenuEntity menu = new SysMenuEntity().update(this.getUserId());
        menu.setId(id);
        menu.setDataStatus(0);
        return sysMenuMapper.update(menu);
    }


}
