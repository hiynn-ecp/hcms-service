package com.hiynn.cms.controller;

import com.hiynn.cms.service.SysDataSourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 代码生成
 *
 * @author 张朋
 * @date 2019/11/7 15:54
 */
@RestController
@RequestMapping("sys/code/generator")
@Api(tags = "D-[代码生成]-控制器")
public class SysGeneratorController {

    private final SysDataSourceService sysDataSourceService;

    @Autowired
    public SysGeneratorController(SysDataSourceService sysDataSourceService) {
        this.sysDataSourceService = sysDataSourceService;
    }

    /**
     * 生成代码
     */
    @RequiresPermissions("CodeGenerator_Get_generate")
    @GetMapping("generate")
    @ApiOperation(value = "代码生成下载", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> generate(
            @ApiParam(name = "dataSourceId", value = "数据源id", required = true)
            @RequestParam String dataSourceId,
            @ApiParam(name = "author", value = "作者")
            @RequestParam(required = false) String author,
            @ApiParam(name = "packagePath", value = "包定义")
            @RequestParam(required = false) String packagePath,
            @ApiParam(name = "tables", value = "表名集合")
            @RequestParam(required = false) List<String> tables,
            @ApiParam(name = "tablePrefix", value = "要去除的表前缀")
            @RequestParam(required = false) String tablePrefix
    ) {
        byte[] data = sysDataSourceService.generatorCode(dataSourceId, tables, author, packagePath, tablePrefix);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", "attachment; filename=code.zip");
        headers.add("Location", "code.zip");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("FileName", "code.zip");
        headers.add("Access-Control-Expose-Headers", "FileName");
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(data.length)
                .contentType(MediaType.parseMediaType(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                .body(data);
    }
}
