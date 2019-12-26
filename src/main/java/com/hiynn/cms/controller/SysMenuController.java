package com.hiynn.cms.controller;

import com.hiynn.cms.common.HCMSConstants;
import com.hiynn.cms.common.aop.syslog.SysLog;
import com.hiynn.cms.common.util.BeanUtils;
import com.hiynn.cms.dao.ValidatorMapper;
import com.hiynn.cms.entity.SysMenuEntity;
import com.hiynn.cms.model.dto.MenuDto;
import com.hiynn.cms.model.dto.RoleMenusDTO;
import com.hiynn.cms.model.vo.MenuVO;
import com.hiynn.cms.service.SysMenuService;
import com.hiynn.component.common.core.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 目录/菜单/按钮/表
 *
 * @author 张朋
 * @date 2019-10-24 17:19:53
 */
@Api(tags = "A-[目录/菜单/按钮]-控制器")
@RestController
@RequestMapping("sys/menu")
@Slf4j
@Validated
public class SysMenuController {

    private final SysMenuService sysMenuService;
    private final ValidatorMapper validatorMapper;

    @Autowired
    public SysMenuController(SysMenuService sysMenuService, ValidatorMapper validatorMapper) {
        this.sysMenuService = sysMenuService;
        this.validatorMapper = validatorMapper;
    }

    /**
     * 当前用户菜单树
     */
    @GetMapping(value = "user/tree")
    @ApiOperation(value = "当前用户菜单树", notes = "获取当前用户菜单树", response = MenuVO.class)
    public Result listTreeByUserId() {
        return Result.success(sysMenuService.listTreeByUserId());
    }

    /**
     * 所有菜单树
     */
    @RequiresPermissions("Menu_Get_tree")
    @GetMapping(value = "tree")
    @ApiOperation(value = "所有菜单树", response = MenuVO.class)
    public Result listTree() {
        return Result.success(sysMenuService.listTree());
    }


    /**
     * 获取某个角色菜单树
     */
    @RequiresPermissions("Menu_Get_role_tree")
    @GetMapping(value = "role/tree/{roleId}")
    @ApiOperation(value = "获取某个角色菜单树", response = MenuVO.class)
    public Result listTreeByRoleId(@ApiParam(name = "roleId", value = "角色id", required = true)
                                   @PathVariable @Length(min = 1, max = 32, message = "角色ID无效，长度1-32") String roleId) {

        int role = validatorMapper.isRole(roleId);
        if (role == 0) {
            return Result.errorParam().setReturnMessage("角色ID无效");
        }
        return Result.success(sysMenuService.listTreeByRoleId(roleId));
    }

    /**
     * 给某个角色分配一组菜单
     */
    @RequiresPermissions("Menu_Post_role")
    @SysLog
    @PostMapping("role")
    @ApiOperation(value = "给某个角色分配一组菜单")
    public Result insertMenusToRole(@Validated(Insert.class) @RequestBody RoleMenusDTO roleMenus) {
        // 校验角色id有效性
        int role = validatorMapper.isRole(roleMenus.getRoleId());
        if (role == 0) {
            return Result.errorParam().setReturnMessage("roleId无效，或角色被禁用。");
        }
        Set<String> menuIds = roleMenus.getMenuIds();
        // 如果分配菜单 就校验有效性
        if (menuIds != null) {
            for (String menuId : menuIds) {
                int menu = validatorMapper.isMenu(menuId);
                if (menu == 0) {
                    return Result.errorParam().setReturnMessage("Menuid无效，或产品被禁用:" + menuId);
                }
            }
        }
        return Result.success(sysMenuService.insertRoleMenus(roleMenus));
    }

    /**
     * 按钮权限列表
     */
    @GetMapping(value = "perms")
    @ApiOperation(value = "获取当前用户的权限列表", notes = "前端使用", response = Set.class)
    public Result listPerms() {
        List<SysMenuEntity> sysMenuEntities = sysMenuService.listPermsByUser();
        Set<String> perms = null;
        // 每个实体中的 perms属性提取到set集合中 返回集合列表
        if (sysMenuEntities != null) {
            perms = sysMenuEntities.stream().map(SysMenuEntity::getPerms).collect(Collectors.toSet());
        }
        return Result.success(perms);
    }

    /**
     * 分页查询列表
     */
    @RequiresPermissions("Menu_Get_list_page")
    @GetMapping(value = "list/page")
    @ApiOperation(value = "分页查询列表", notes = "分页查询列表，不传则查询全部", response = MenuVO.class)
    public Result selectByPage(
            @ApiParam(name = "page", value = "页码")
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @ApiParam(name = "pageSize", value = "数量")
            @RequestParam(defaultValue = "0", required = false) Integer pageSize) {
        return Result.success().setData(
                sysMenuService.listPage(page, pageSize)
        );
    }

    /**
     * ID查询
     */
    @RequiresPermissions("Menu_Get_id")
    @GetMapping("{id}")
    @ApiOperation(value = "根据id查询单个菜单", response = MenuVO.class)
    public Result selectById(
            @ApiParam(name = "id", value = "主键ID", required = true)
            @PathVariable("id") @Length(min = 1, max = 32, message = "id无效，长度1=32") String id) {
        SysMenuEntity select = sysMenuService.select(id);
        if (select == null) {
            return HCMSConstants.ResultTemplate.EXECUT_ERROR;
        }
        return Result.success().setData(
                BeanUtils.copy(select, MenuVO.class)
        );
    }

    /**
     * 保存
     */
    @RequiresPermissions("Menu_Post")
    @SysLog
    @PostMapping()
    @ApiOperation(value = "保存", notes = "保存")
    public Result insert(@RequestBody() @Validated(Insert.class) MenuDto menu) {
        sysMenuService.insert(menu);
        return Result.success();
    }

    /**
     * 修改
     */
    @RequiresPermissions("Menu_Put")
    @SysLog
    @PutMapping()
    @ApiOperation(value = "更新", notes = "更新")
    public Result update(@RequestBody() @Validated(Update.class) MenuDto menuDto) {
        int update = sysMenuService.update(menuDto);
        if (update == 0) {
            return HCMSConstants.ResultTemplate.EXECUT_ERROR;
        }
        return Result.success();
    }

    /**
     * 删除
     */
    @RequiresPermissions("Menu_Delete")
    @SysLog
    @DeleteMapping("{id}")
    @ApiOperation(value = "删除", notes = "删除")
    public Result delete(@ApiParam(name = "id", value = "主键id")
                         @PathVariable("id") @Length(min = 1, max = 32, message = "id无效,长度32") String id) {
        int delete = sysMenuService.delete(id);
        if (delete == 0) {
            return HCMSConstants.ResultTemplate.EXECUT_ERROR;
        }
        return Result.success();
    }

}
