package com.hiynn.cms.controller;

import com.github.pagehelper.PageInfo;
import com.hiynn.cms.common.HCMSConstants;
import com.hiynn.cms.common.aop.syslog.SysLog;
import com.hiynn.cms.common.util.BeanUtils;
import com.hiynn.cms.entity.SysPlatformProductEntity;
import com.hiynn.cms.model.dto.PlatformProductDTO;
import com.hiynn.cms.model.vo.PlatformProductVO;
import com.hiynn.cms.service.SysPlatformProductService;
import com.hiynn.component.common.core.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 平台产品
 *
 * @author 张朋
 * @date 2019-11-12 18:32:50
 */
@Api(tags = "E-[平台产品]-控制器")
@RestController
@RequestMapping("sys/platform/product")
@Slf4j
public class SysPlatformProductController {

    private final SysPlatformProductService sysPlatformProductService;

    @Autowired
    public SysPlatformProductController(SysPlatformProductService sysPlatformProductService) {
        this.sysPlatformProductService = sysPlatformProductService;
    }

    /**
     * 分页查询列表
     */
    @RequiresPermissions("PlatformProduct_Get_list_page")
    @GetMapping(value = "list/page")
    @ApiOperation(value = "分页查询列表", notes = "分页查询列表", response = PlatformProductVO.class)
    public Result selectByPage(
            @ApiParam(name = "page", value = "页码",defaultValue = "0")
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @ApiParam(name = "pageSize", value = "数量",defaultValue = "0")
            @RequestParam(defaultValue = "0", required = false) Integer pageSize) {
        // 分页数据
        PageInfo<SysPlatformProductEntity> pageInfo = sysPlatformProductService.listPage(page, pageSize);
        // 新数据
        List<SysPlatformProductEntity> targetList = new ArrayList<>();
        for (SysPlatformProductEntity sysPlatformProductEntity : pageInfo.getList()) {
            // 转换为视图模型
            PlatformProductVO copy = BeanUtils.copy(sysPlatformProductEntity, PlatformProductVO.class);
            // add 到新数据集合
            targetList.add(copy);
        }
        // 覆盖原数据
        pageInfo.setList(targetList);
        return Result.success().setData(pageInfo);
    }


    /**
     * ID查询
     */
    @RequiresPermissions("PlatformProduct_Get_id")
    @GetMapping("{id}")
    @ApiOperation(value = "id查询单个数据", notes = "id查询单个数据", response = PlatformProductVO.class)
    public Result selectById(
            @ApiParam(name = "id", value = "主键ID", required = true)
            @PathVariable("id") String id) {
        SysPlatformProductEntity select = sysPlatformProductService.select(id);
        PlatformProductVO copy = BeanUtils.copy(select, PlatformProductVO.class);
        return Result.success().setData(copy);
    }

    /**
     * 保存
     */
    @RequiresPermissions("PlatformProduct_Post")
    @SysLog
    @PostMapping
    @ApiOperation(value = "保存", notes = "保存")
    public Result insert(PlatformProductDTO dto) {
        sysPlatformProductService.insert(BeanUtils.copy(dto, SysPlatformProductEntity.class));
        return Result.success();
    }

    /**
     * 修改
     */
    @RequiresPermissions("PlatformProduct_Put")
    @SysLog
    @PutMapping
    @ApiOperation(value = "更新", notes = "更新")
    public Result update(PlatformProductDTO dto) {
        int cnt = sysPlatformProductService.update(BeanUtils.copy(dto, SysPlatformProductEntity.class));
        if (cnt == 0) {
            return HCMSConstants.ResultTemplate.EXECUT_ERROR;
        }
        return Result.success();
    }

    /**
     * 删除
     */
    @RequiresPermissions("PlatformProduct_Delete")
    @SysLog
    @ApiOperation(value = "删除", notes = "删除")
    @DeleteMapping("{id}")
    public Result delete(
            @ApiParam(name = "id", value = "主键ID", required = true)
            @PathVariable("id") String id) {
        int cnt = sysPlatformProductService.delete(id);
        if (cnt == 0) {
            Result.errorParam();
        }
        return Result.success();
    }

}
