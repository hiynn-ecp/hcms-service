package com.hiynn.cms.controller;

import com.github.pagehelper.PageInfo;
import com.hiynn.cms.common.HCMSConstants;
import com.hiynn.cms.common.aop.syslog.SysLog;
import com.hiynn.cms.common.util.BeanUtils;
import com.hiynn.cms.dao.ValidatorMapper;
import com.hiynn.cms.entity.SysWorkPlatformEntity;
import com.hiynn.cms.model.dto.WorkPlatformDTO;
import com.hiynn.cms.model.vo.WorkPlatformVO;
import com.hiynn.cms.service.SysWorkPlatformService;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 工作平台
 *
 * @author 张朋
 * @date 2019-11-12 18:32:51
 */
@Api(tags = "F-[工作平台]-控制器")
@RestController
@RequestMapping("sys/work/platform")
@Slf4j
public class SysWorkPlatformController {

    private final SysWorkPlatformService sysWorkPlatformService;
    private final ValidatorMapper validatorMapper;

    @Autowired
    public SysWorkPlatformController(SysWorkPlatformService sysWorkPlatformService,
                                     ValidatorMapper validatorMapper) {
        this.sysWorkPlatformService = sysWorkPlatformService;
        this.validatorMapper = validatorMapper;
    }

    /**
     * 分页查询列表
     */
    @GetMapping(value = "list/page")
    @RequiresPermissions("WorkPlatform_Get_list_page")
    @ApiOperation(value = "分页查询列表", notes = "分页查询列表")
    public Result selectByPage(
            @ApiParam(name = "page", value = "页码")
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @ApiParam(name = "pageSize", value = "数量")
            @RequestParam(defaultValue = "0", required = false) Integer pageSize) {

        PageInfo<SysWorkPlatformEntity> pageInfo = sysWorkPlatformService.listPage(page, pageSize);

        List<SysWorkPlatformEntity> list = pageInfo.getList();
        //  结果转换 去除无用参数
        for (int i = 0; i < list.size(); i++) {
            list.set(i, BeanUtils.copy(list.get(i), WorkPlatformVO.class));
        }
        return Result.success().setData(pageInfo);
    }

    /**
     * ID查询
     */
    @RequiresPermissions("WorkPlatform_Get_id")
    @GetMapping("{id}")
    @ApiOperation(value = "ID查询", response = WorkPlatformVO.class)
    public Result selectById(
            @ApiParam(name = "id", value = "主键ID", required = true)
            @PathVariable("id") @Length(message = "id无效，长度1-32") String id) {
        SysWorkPlatformEntity select = sysWorkPlatformService.select(id);
        if (select == null) {
            return HCMSConstants.ResultTemplate.EXECUT_ERROR;
        }
        return Result.success().setData(BeanUtils.copy(select, WorkPlatformVO.class));
    }

    /**
     * 保存
     */
    @RequiresPermissions("WorkPlatform_Post")
    @SysLog
    @PostMapping
    @ApiOperation(value = "保存")
    public Result insert(@Validated(Insert.class) WorkPlatformDTO workPlatformDTO,
                         @ApiParam(name = "icon", value = "平台产品图标")
                         @RequestParam MultipartFile icon) {
        sysWorkPlatformService.insert(BeanUtils.copy(workPlatformDTO, SysWorkPlatformEntity.class), icon);
        return Result.success();
    }

    /**
     * 修改
     */
    @RequiresPermissions("WorkPlatform_Put")
    @SysLog
    @PutMapping
    @ApiOperation(value = "更新")
    public Result update(@Validated(Update.class) WorkPlatformDTO workPlatformDTO, @RequestParam(required = false) MultipartFile icon) {
        int update = sysWorkPlatformService.update(BeanUtils.copy(workPlatformDTO, SysWorkPlatformEntity.class), icon);
        if (update == 0) {
            return HCMSConstants.ResultTemplate.EXECUT_ERROR;
        }
        return Result.success();
    }

    /**
     * 删除
     */
    @RequiresPermissions("WorkPlatform_Delete")
    @SysLog
    @DeleteMapping("{id}")
    @ApiOperation(value = "删除")
    public Result delete(
            @ApiParam(name = "id", value = "主键ID", required = true)
            @PathVariable("id") @Length(message = "id长度1-32", min = 1, max = 32) String id) {
        int update = sysWorkPlatformService.delete(id);
        if (update == 0) {
            return HCMSConstants.ResultTemplate.EXECUT_ERROR;
        }
        return Result.success();
    }

}
