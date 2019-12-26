package com.hiynn.cms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hiynn.cms.common.util.BeanUtils;
import com.hiynn.cms.common.util.IDUtils;
import com.hiynn.cms.dao.SysRoleMapper;
import com.hiynn.cms.entity.SysRoleEntity;
import com.hiynn.cms.entity.SysUserRoleREntity;
import com.hiynn.cms.model.dto.RoleDTO;
import com.hiynn.cms.model.dto.UserRolesDTO;
import com.hiynn.cms.model.vo.PageData;
import com.hiynn.cms.model.vo.RoleVO;
import com.hiynn.cms.service.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 角色表
 *
 * @author 张朋
 * @date 2019-10-24 17:19:53
 */
@Service
@Slf4j
public class SysRoleServiceImpl implements SysRoleService {


    private final SysRoleMapper sysRoleMapper;

    @Autowired
    public SysRoleServiceImpl(SysRoleMapper sysRoleMapper) {
        this.sysRoleMapper = sysRoleMapper;
    }


    /**
     * 获取用户角色
     *
     * @param userId
     * @return java.util.List<com.hiynn.cms.service.SysRoleService>
     * @author 张朋
     * @date 2019/10/28 14:26
     */
    @Override
    public List<SysRoleEntity> listByUserId(String userId) {
        return sysRoleMapper.listByUserId(userId);
    }

    /**
     * 插入用户一组角色
     *
     * @param userRolesDTO
     * @return int
     * @author 张朋
     * @date 2019/11/4 18:04
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int insertUserRoles(UserRolesDTO userRolesDTO) {
        String userId = userRolesDTO.getUserId();
        // 清除原关系
        sysRoleMapper.deleteUserRole(userId);
        Date nowDate = new Date();
        Set<String> roles = userRolesDTO.getRoles();
        if (roles != null) {
            // 重新分配关系
            for (String roleId : roles) {
                SysUserRoleREntity userRole = new SysUserRoleREntity();
                userRole.setUserId(userId);
                userRole.setId(IDUtils.getJavaUUID());
                userRole.setRoleId(roleId);
                userRole.setCreateTime(nowDate);
                sysRoleMapper.insertUserRole(userRole);
            }
        }
        return 1;
    }

    @Override
    public SysRoleEntity select(String id) {
        return sysRoleMapper.select(id);
    }

    @Override
    public PageInfo<SysRoleEntity> listPage(Integer page, Integer pageSize) {
        // 分页
        PageHelper.startPage(page, pageSize);
        // 查
        List<SysRoleEntity> selectByPage = sysRoleMapper.list();
        // 重写分页对象
        PageInfo<SysRoleEntity> pageInfo = new PageData<>(selectByPage);
        // 属性拷贝
        List<SysRoleEntity> roleList = new ArrayList<>();
        for (SysRoleEntity sysRole : pageInfo.getList()) {
            roleList.add(BeanUtils.copy(sysRole, RoleVO.class));
        }
        // 更新分页结果
        pageInfo.setList(roleList);

        return pageInfo;
    }

    @Override
    public int countTotal() {
        return sysRoleMapper.countTotal();
    }

    @Override
    public int insert(RoleDTO role) {
        // 属性拷贝、新增补全
        SysRoleEntity sysRole = BeanUtils.copy(role, SysRoleEntity.class).insert(this.getUserId());
        return sysRoleMapper.insert(sysRole);
    }

    @Override
    public int update(RoleDTO role) {
        // 属性拷贝、新增补全
        SysRoleEntity sysRole = BeanUtils.copy(role, SysRoleEntity.class).update(this.getUserId());
        return sysRoleMapper.update(sysRole);
    }

    @Override
    public int delete(String id) {
        SysRoleEntity sysRole = new SysRoleEntity().update(this.getUserId());
        sysRole.setId(id);
        sysRole.setDataStatus(0);
        return sysRoleMapper.update(sysRole);
    }


}
