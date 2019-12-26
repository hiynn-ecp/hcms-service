package com.hiynn.cms.controller;

import com.hiynn.cms.common.HCMSConstants;
import com.hiynn.cms.common.aop.syslog.SysLog;
import com.hiynn.cms.common.util.BeanUtils;
import com.hiynn.cms.entity.SysDataSourceEntity;
import com.hiynn.cms.model.dto.DataSourceDTO;
import com.hiynn.cms.model.vo.DataSourceTableVO;
import com.hiynn.cms.model.vo.DataSourceVO;
import com.hiynn.cms.service.SysDataSourceService;
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

import java.sql.SQLException;
import java.util.List;

/**
 * 数据源控制器
 *
 * @author 张朋
 * @date 2019-10-24 17:19:52
 */
@Api(tags = "D-[数据源]-控制器")
@RestController
@RequestMapping("sys/datasource")
@Slf4j
@Validated
public class SysDataSourceController {

    private final SysDataSourceService dataSourceService;

    @Autowired
    public SysDataSourceController(SysDataSourceService dataSourceService) {
        this.dataSourceService = dataSourceService;
    }

    @GetMapping("list")
    @ApiOperation(response = DataSourceVO.class, value = "获取数据源列表", notes = "数据源id 用户回传查询某个数据源的数据表集合")
    public Result listDataSource() {
        List<DataSourceVO> dataSourceVOS = dataSourceService.listDataSource();
        return Result.success(dataSourceVOS);
    }

    @RequiresPermissions("DataSource_Get_list_table")
    @GetMapping("list/table")
    @ApiOperation(response = DataSourceTableVO.class, value = "获取指定数据源的数据表", notes = "传入数据源id ，表名称关键字为模糊查询，可不传")
    public Result listDataSourceTable(
            @ApiParam(name = "dataSourceId", value = "数据源id", required = true)
            @RequestParam
            @Length(max = 32, message = "参数无效") String dataSourceId,
            @ApiParam(name = "tableName", value = "模糊查询关键字")
            @RequestParam(required = false) String tableName
    ) throws SQLException {
        return Result.success(dataSourceService.listTablesByDataSource(dataSourceId, tableName));
    }

    @SysLog
    @ApiOperation(value = "删除数据源", notes = "占位传参 id为uri一部分")
    @DeleteMapping("{id}")
    public Result delete(@PathVariable("id")
                         @Length(min = 1, max = 32, message = "参数无效") String id) {
        if (0 == dataSourceService.deleteByPrimaryKey(id)) {
            return HCMSConstants.ResultTemplate.EXECUT_ERROR;
        }
        return Result.success();
    }

    @SysLog
    @ApiOperation(value = "新增数据源", notes = "JSON请求传参")
    @PostMapping
    public Result insert(@Validated(Insert.class) DataSourceDTO dataSourceDTO,
                         @RequestParam(required = false) MultipartFile[] files) {
        dataSourceService.insertSelective(BeanUtils.copy(dataSourceDTO, SysDataSourceEntity.class),files);
        return Result.success();
    }

    @SysLog
    @ApiOperation(value = "修改数据源", notes = "JSON请求传参")
    @PutMapping
    public Result update(@Validated(Update.class) DataSourceDTO dataSourceDTO,
                         @RequestParam(required = false) MultipartFile[] files) {
        int cnt = dataSourceService.updateByPrimaryKeySelective(dataSourceDTO, files);

        if (0 == cnt) {
            return HCMSConstants.ResultTemplate.EXECUT_ERROR;
        }
        return Result.success();
    }

    @ApiOperation(value = "根据ID查询单个数据源", response = SysDataSourceEntity.class)
    @GetMapping("{id}")
    public Result select(
            @ApiParam(name = "id", value = "主键ID")
            @PathVariable("id")
            @Length(min = 1, max = 32, message = "参数无效") String id) {
        SysDataSourceEntity dataSourceEntity = dataSourceService.selectByPrimaryKey(id);
        return Result.success(dataSourceEntity);
    }

    @SysLog
    @ApiOperation(value = "刷新数据源缓存")
    @PutMapping("refresh")
    public Result refresh() {
        // 刷新
        dataSourceService.refresh();
        return Result.success();
    }

    /**
     * 描述: 重置数据库
     * @author liuhy
     * @date 2019/12/12 15:09
     * @param
     * @return com.hiynn.component.common.core.Result
     */
    @SysLog
    @ApiOperation(value = "重置数据源数据")
    @PutMapping("reset/{id}")
    public Result resetData(@PathVariable("id")
                                @Length(min = 1, max = 32, message = "参数无效") String id){
        dataSourceService.resetDataSource(id);
        return Result.success();
    }

}
