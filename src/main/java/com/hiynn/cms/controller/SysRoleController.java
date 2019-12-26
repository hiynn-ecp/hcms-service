package com.hiynn.cms.controller;

import com.hiynn.cms.common.HCMSConstants;
import com.hiynn.cms.common.aop.syslog.SysLog;
import com.hiynn.cms.common.util.BeanUtils;
import com.hiynn.cms.dao.ValidatorMapper;
import com.hiynn.cms.entity.SysRoleEntity;
import com.hiynn.cms.model.dto.RoleDTO;
import com.hiynn.cms.model.dto.UserRolesDTO;
import com.hiynn.cms.model.vo.RoleVO;
import com.hiynn.cms.service.SysRoleService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 角色表
 *
 * @author 张朋
 * @date 2019-10-24 17:19:53
 */
@Api(tags = "B-[角色]-控制器")
@RestController
@RequestMapping("sys/role")
@Slf4j
@Validated
public class SysRoleController {

    private final SysRoleService sysRoleService;
    private final ValidatorMapper validatorMapper;

    @Autowired
    public SysRoleController(SysRoleService sysRoleService,
                             ValidatorMapper validatorMapper) {
        this.sysRoleService = sysRoleService;
        this.validatorMapper = validatorMapper;
    }

    /**
     * 保存
     */
    @RequiresPermissions("Role_Post")
    @SysLog
    @PostMapping()
    @ApiOperation(value = "新增")
    public Result insert(@ApiParam(name = "role", value = "角色JSON对象")
                         @RequestBody() @Validated(Insert.class) RoleDTO role) {
        sysRoleService.insert(role);
        return Result.success();
    }


    /**
     * 分页查询列表
     */
    @RequiresPermissions("Role_Get_list_page")
    @GetMapping(value = "list/page")
    @ApiOperation(value = "角色列表", notes = "不传参数则查询全部", response = RoleVO.class)
    public Result listPage(
            @ApiParam(name = "page", value = "页码")
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @ApiParam(name = "pageSize", value = "每页数量")
            @RequestParam(defaultValue = "0", required = false) Integer pageSize) {
        return Result.success().setData(sysRoleService.listPage(page, pageSize));
    }

    /**
     * 给指定用户分配一组角色
     */
    @RequiresPermissions("Role_Post_user")
    @SysLog
    @PostMapping("user")
    @ApiOperation(value = "分配角色到用户", notes = "一个用户多个角色,配合分页查询使用 不传参数查询所有")
    public Result rolesToUser(@RequestBody @Validated(Insert.class) UserRolesDTO userRolesDTO) {
        // 用户有效性校验
        String userId = userRolesDTO.getUserId();
        if (validatorMapper.isUser(userId) == 0) {
            return Result.errorParam().setReturnMessage("用户无效");
        }

        // 角色有效性校验
        Set<String> roles = userRolesDTO.getRoles();
        for (String roleId : roles) {
            int roleCnt = validatorMapper.isRole(roleId);
            if (roleCnt == 0) {
                return Result.errorParam().setReturnMessage("角色无效或被禁用：" + roleId);
            }
        }
        int cnt = sysRoleService.insertUserRoles(userRolesDTO);
        if(cnt == 0 ){
            return HCMSConstants.ResultTemplate.EXECUT_ERROR;
        }
        return Result.success();
    }

    /**
     * ID查询
     */
    @RequiresPermissions("Role_Get_id")
    @GetMapping("{id}")
    @ApiOperation(value = "id查询角色", response = RoleVO.class)
    public Result selectById(
            @ApiParam(name = "id", value = "主键ID", required = true)
            @PathVariable("id") @Length(min = 1, max = 32, message = "id无效，长度1-32") String id) {
        SysRoleEntity select = sysRoleService.select(id);
        if (select == null) {
            return HCMSConstants.ResultTemplate.EXECUT_ERROR;
        }
        return Result.success().setData(BeanUtils.copy(select, RoleVO.class));
    }


    /**
     * 获取某个用户的角色
     */
    @RequiresPermissions("Role_Get_list_user")
    @GetMapping("list/user/{userId}")
    @ApiOperation(value = "用户id查询一组角色", response = RoleVO.class)
    public Result lisRleByUserId(@ApiParam(name = "userId", value = "用户id", required = true)
                                 @PathVariable("userId")
                                 @Length(message = "userId无效，长度1-32", min = 1, max = 32) String userId) {

        // 用户校验
        int user = validatorMapper.isUser(userId);
        if (user == 0) {
            return Result.errorParam().setReturnMessage("用户无效或被禁用");
        }

        List<SysRoleEntity> sysRoleEntities = sysRoleService.listByUserId(userId);

        List<RoleVO> roleVOS = new ArrayList<>();
        for (SysRoleEntity sysRoleEntity : sysRoleEntities) {
            roleVOS.add(BeanUtils.copy(sysRoleEntity, RoleVO.class));
        }
        return Result.success().setData(roleVOS);
    }

    /**
     * 修改
     */
    @RequiresPermissions("Role_Put")
    @SysLog
    @PutMapping()
    @ApiOperation(value = "更新", notes = "更新")
    public Result update(@RequestBody() @Validated(Update.class) RoleDTO role) {
        int update = sysRoleService.update(role);
        // 失败
        if (0 == update) {
            return HCMSConstants.ResultTemplate.EXECUT_ERROR;
        }

        return Result.success();
    }

    /**
     * 删除
     */
    @RequiresPermissions("Role_Delete")
    @SysLog
    @DeleteMapping("{id}")
    @ApiOperation(value = "删除", notes = "删除")
    public Result delete(@PathVariable("id") @Length(min = 1, max = 32, message = "id无效，长度1-32") String id) {
        int delete = sysRoleService.delete(id);
        // 失败
        if (0 == delete) {
            return HCMSConstants.ResultTemplate.EXECUT_ERROR;
        }
        return Result.success();
    }

}
