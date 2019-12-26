package com.hiynn.cms.controller;

import com.hiynn.cms.entity.SysLogEntity;
import com.hiynn.cms.service.SysLogService;
import com.hiynn.component.common.core.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * 系统日志记录表
 *
 * @author 张朋
 * @date 2019-11-12 11:17:04
 */
@Api(tags = "A-[操作日志]-控制器")
@RestController
@RequestMapping("sys/log")
@Slf4j
@Validated
public class SysLogController {

    private final SysLogService sysLogService;

    @Autowired
    public SysLogController(SysLogService sysLogService) {
        this.sysLogService = sysLogService;
    }

    /**
     * 分页查询列表
     */
    @RequiresPermissions("Log_Get_list_page")
    @GetMapping(value = "list/page")
    @ApiOperation(response = SysLogEntity.class, value = "分页查询列表", notes = "多功能查询接口，" +
            "不传参数可查询所有")
    public Result listPage(
            @ApiParam(name = "page", value = "页码")
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @ApiParam(name = "pageSize", value = "数量")
            @RequestParam(defaultValue = "0", required = false) Integer pageSize,
            @ApiParam(name = "searchKey", value = "用户登陆名/用户昵称/产品描述/操作描述等关键字搜索， 选填，长度30")
            @RequestParam(required = false) @Length(max = 30, message = "searchKey 最大长度30 ") String searchKey,
            @ApiParam(name = "startTime", value = "开始时间与结束时间同时存在 才有效 yyyy/MM/dd HH:mm:ss")
            @RequestParam(required = false) Date startTime,
            @ApiParam(name = "endTime", value = "结束时间与结束时间同时存在 才有效 yyyy/MM/dd HH:mm:ss")
            @RequestParam(required = false) Date endTime) {

        if (startTime != null && endTime != null && startTime.getTime() >= endTime.getTime()) {
            return Result.errorParam().setReturnMessage("结束时间需大于等于开始时间");
        }

        return Result.success().setData(sysLogService.selectForList(page, pageSize, searchKey, startTime, endTime));
    }

    /**
     * ID查询
     */
    @RequiresPermissions("Log_Get_id")
    @GetMapping("{id}")
    @ApiOperation(value = "ID查询单条数据", response = SysLogEntity.class)
    public Result select(
            @ApiParam(name = "id", value = "主键ID", required = true)
            @PathVariable("id") @Length(max = 32, min = 1, message = "ID无效，ID长度1-32") String id) {
        SysLogEntity select = sysLogService.select(id);
        if (select == null) {
            return Result.errorParam().setReturnMessage("ID无效");
        }
        return Result.success().setData(select);
    }

}
