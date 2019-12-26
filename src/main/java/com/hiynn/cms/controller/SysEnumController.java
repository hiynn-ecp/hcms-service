package com.hiynn.cms.controller;

import com.github.pagehelper.PageInfo;
import com.hiynn.cms.common.HCMSConstants;
import com.hiynn.cms.common.aop.syslog.SysLog;
import com.hiynn.cms.entity.SysEnumEntity;
import com.hiynn.cms.service.SysEnumService;
import com.hiynn.cms.validator.HCMSValidator;
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

/**
 * 系统枚举表
 *
 * @author 张朋
 * @date 2019-11-11 10:48:04
 */
@Api(tags = "A-[枚举管理]-控制器")
@RestController
@RequestMapping("sys/enum")
@Slf4j
@Validated
public class SysEnumController {

    private final SysEnumService sysEnumService;


    @Autowired
    public SysEnumController(SysEnumService sysEnumService) {
        this.sysEnumService = sysEnumService;
    }

    /**
     * 分页查询列表
     */
    @RequiresPermissions("Enum_Get_list_page")
    @GetMapping(value = "list/page")
    @ApiOperation(value = "分页查询列表", notes = "不传参数则查询所有", response = SysEnumEntity.class)
    public Result selectByPage(
            @ApiParam(name = "page", value = "页码")
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @ApiParam(name = "pageSize", value = "数量")
            @RequestParam(defaultValue = "0", required = false) Integer pageSize,
            @ApiParam(name = "type", value = "枚举类型")
            @RequestParam(required = false) @Length(max = 100, min = 1, message = "枚举类型最大长度100") String type
    ) {
        PageInfo<?> pageInfo = sysEnumService.listPage(page, pageSize, type);
        if (pageInfo.getList().size() == 0) {
            HCMSValidator.paramError();
        }
        return Result.success().setData(pageInfo);
    }

    /**
     * 分页查询列表
     */
    @RequiresPermissions("Enum_Get_list_type")
    @GetMapping(value = "list/type")
    @ApiOperation(value = "枚举类型获取枚举", notes = "传入枚举类型获取一组枚举", response = SysEnumEntity.class)
    public Result listByType(
            @ApiParam(name = "type", value = "枚举类型", required = true)
            @RequestParam @Length(max = 100, min = 1, message = "枚举类型最大长度100") String type) {

        List<SysEnumEntity> sysEnumEntities = sysEnumService.listByType(type);

        if (sysEnumEntities.size() == 0) {
            HCMSValidator.paramError();
        }

        return Result.success().setData(sysEnumEntities);
    }


    /**
     * ID查询
     */
    @RequiresPermissions("Enum_Get_id")
    @GetMapping("{id}")
    @ApiOperation(value = "ID查询", response = SysEnumEntity.class)
    public Result selectById(
            @ApiParam(name = "id", value = "主键ID", required = true)
            @PathVariable("id") @Length(max = 32, min = 1, message = "主键长度最大32") String id
    ) {
        SysEnumEntity select = sysEnumService.select(id);
        if (select == null) {
            HCMSValidator.paramError();
        }
        return Result.success().setData(select);
    }

    /**
     * 保存
     */
    @RequiresPermissions("Enum_Post_insert")
    @SysLog
    @PostMapping
    @ApiOperation(value = "保存", notes = "保存")
    public Result insert(@RequestBody() @Validated(Insert.class) SysEnumEntity sysEnum) {
        sysEnumService.insert(sysEnum);
        return Result.success();
    }

    /**
     * 修改
     */
    @RequiresPermissions("Enum_Put_update")
    @SysLog
    @PutMapping()
    @ApiOperation(value = "更新", notes = "更新")
    public Result update(@RequestBody() @Validated(Update.class) SysEnumEntity sysEnum) {
        if (0 == sysEnumService.update(sysEnum)) {
            return HCMSConstants.ResultTemplate.EXECUT_ERROR;
        }
        return Result.success();
    }


    @RequiresPermissions("Enum_Delete")
    @SysLog
    @DeleteMapping("{id}")
    @ApiOperation(value = "删除枚举")
    public Result delete(@ApiParam(name = "id", value = "主键ID", required = true)
                         @PathVariable("id") @Length(max = 32, min = 1, message = "ID最大长度32") String id) {
        if (0 == sysEnumService.delete(id)) {
            return HCMSConstants.ResultTemplate.EXECUT_ERROR;
        }
        return Result.success();
    }


}
