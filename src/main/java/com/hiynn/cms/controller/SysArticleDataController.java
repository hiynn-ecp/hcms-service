package com.hiynn.cms.controller;

import com.github.pagehelper.PageInfo;
import com.hiynn.cms.common.HCMSConstants;
import com.hiynn.cms.common.aop.syslog.SysLog;
import com.hiynn.cms.common.util.BeanUtils;
import com.hiynn.cms.entity.SysArticleDataEntity;
import com.hiynn.cms.entity.SysArticleDataFilesEntity;
import com.hiynn.cms.model.dto.ArticleDataDTO;
import com.hiynn.cms.model.vo.ArticleDataVO;
import com.hiynn.cms.service.SysArticleDataFilesService;
import com.hiynn.cms.service.SysArticleDataService;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 文章资料 带附件 分类查询 可存储一切文件
 *
 * @author 张朋
 * @date 2019-11-12 18:32:50
 */
@Api(tags = "G-[文章资料]-控制器")
@RestController
@RequestMapping("sys/article/data")
@Slf4j
@Validated
public class SysArticleDataController {

    private final SysArticleDataService sysArticleDataService;
    private final SysArticleDataFilesService sysArticleDataFilesService;
    private final HCMSValidator hcmsValidator;

    @Autowired
    public SysArticleDataController(SysArticleDataService sysArticleDataService,
                                    SysArticleDataFilesService sysArticleDataFilesService,
                                    HCMSValidator hcmsValidator) {
        this.sysArticleDataService = sysArticleDataService;
        this.sysArticleDataFilesService = sysArticleDataFilesService;
        this.hcmsValidator = hcmsValidator;
    }

    /**
     * 分页查询列表
     */
    @RequiresPermissions("ArticleData_Get_list_page")
    @GetMapping(value = "list/page")
    @ApiOperation(value = "多条件分页查询", notes = "分页查询列表", response = ArticleDataVO.class)
    public Result listByTypeOrTitle(
            @ApiParam(name = "page", value = "页码")
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @ApiParam(name = "pageSize", value = "数量")
            @RequestParam(defaultValue = "0", required = false) Integer pageSize,
            @ApiParam(name = "type", value = "文章资料类型-枚举类型sys_article_data_type", required = true)
            @RequestParam Integer type,
            @ApiParam(name = "title", value = "文章标题关键字-可选")
            @RequestParam(required = false)
            @Length(max = 100, message = "标题无效") String title
    ) {

        // 数据有效性校验
        hcmsValidator.isEnum(type, HCMSConstants.EnumType.ARTICLE_DATA_TYPE);

        PageInfo<SysArticleDataEntity> pageInfo = sysArticleDataService.listByTypeOrTitle(page, pageSize, type, title);
        // 去除无用数据
        List<SysArticleDataEntity> list = pageInfo.getList();
        for (int i = 0; i < list.size(); i++) {
            list.set(i, BeanUtils.copy(list.get(i), ArticleDataVO.class));
        }
        return Result.success().setData(pageInfo);
    }

    /**
     * ID查询
     */
    @RequiresPermissions("ArticleData_Get_id")
    @GetMapping("{id}")
    @ApiOperation(value = "id查询文章资料", response = ArticleDataVO.class)
    public Result selectById(
            @ApiParam(name = "id", value = "主键ID", required = true)
            @PathVariable("id")
            @Length(max = 32, min = 1, message = "参数错误") String id) {
        SysArticleDataEntity select = sysArticleDataService.select(id);
        // 有效性校验
        if (select == null) {
            HCMSValidator.paramError();
        }

        return Result.success(select);
    }

    /**
     * 保存
     */
    @RequiresPermissions("ArticleData_Post")
    @SysLog
    @PostMapping
    @ApiOperation(value = "保存文章资料", notes = "保存-多附件一并保存")
    public Result insert(
            @Validated(Insert.class) ArticleDataDTO dto,
            @RequestParam(required = false) MultipartFile[] files
    ) {
        // 有效性校验
        hcmsValidator.isEnum(dto.getType(), HCMSConstants.EnumType.ARTICLE_DATA_TYPE);
        sysArticleDataService.insert(BeanUtils.copy(dto, SysArticleDataEntity.class), files);
        return Result.success();
    }

    /**
     * 修改
     */
    @RequiresPermissions("ArticleData_Put")
    @SysLog
    @PutMapping
    @ApiOperation(value = "更新", notes = "更新")
    public Result update(
            @Validated(Update.class) ArticleDataDTO dto,
            @RequestParam MultipartFile[] files
    ) {
        // 有效性校验
        hcmsValidator.isEnum(dto.getType(), HCMSConstants.EnumType.ARTICLE_DATA_TYPE);

        int update = sysArticleDataService.update(BeanUtils.copy(dto, SysArticleDataEntity.class), files);
        if (update == 0) {
            return HCMSConstants.ResultTemplate.EXECUT_ERROR;
        }
        return Result.success();
    }

    /**
     * 删除
     */
    @RequiresPermissions("ArticleData_Delete")
    @SysLog
    @DeleteMapping("{id}")
    @ApiOperation(value = "删除文章资料根据ID")
    public Result delete(
            @ApiParam(name = "id", value = "主键ID", required = true)
            @PathVariable("id")
            @Length(max = 32, min = 1, message = "参数错误") String id) {
        int cnt = sysArticleDataService.delete(id);
        if (cnt == 0) {
            return HCMSConstants.ResultTemplate.EXECUT_ERROR;
        }
        return Result.success();
    }


    /**
     * 删除某个文章种的某个附件
     */
    @RequiresPermissions("ArticleData_Delete_files_id")
    @SysLog
    @DeleteMapping("files/{id}")
    @ApiOperation(value = "删除文章中某附件")
    public Result deleteFilesById(
            @ApiParam(name = "id", value = "附件的主键ID", required = true)
            @PathVariable("id")
            @Length(max = 32, min = 1, message = "参数错误") String id) {
        int cnt = sysArticleDataFilesService.delete(id);
        if (cnt == 0) {
            return HCMSConstants.ResultTemplate.EXECUT_ERROR;
        }
        return Result.success();
    }


    /**
     * 某个文章的某个附件
     */
    @RequiresPermissions("ArticleData_Get_files_id")
    @GetMapping("files/{id}")
    @ApiOperation(value = "获取文章中附件组", response = SysArticleDataFilesEntity.class)
    public Result listByArticleDataId(
            @ApiParam(name = "id", value = "文章的主键ID", required = true)
            @PathVariable("id")
            @Length(max = 32, min = 1, message = "参数错误") String id) {

        // 有效性校验
        if (sysArticleDataService.select(id) == null) {
            HCMSValidator.paramError();
        }
        return Result.success(sysArticleDataFilesService.listByArticleDataId(id));
    }


    /**
     * 某个文章的某个附件
     */
    @RequiresPermissions("ArticleData_Get_download_package_files")
    @GetMapping("download/package/files/{id}")
    @ApiOperation(value = "打包下载", produces = "application/zip")
    public void downloadPackageFiles(
            @ApiParam(name = "id", value = "文章的主键ID", required = true)
            @PathVariable("id")
            @Length(max = 32, min = 1, message = "参数错误") String id,
            HttpServletResponse response, HttpServletRequest request) throws IOException {

        SysArticleDataEntity select = sysArticleDataService.select(id);
        // 有效性校验
        if (select == null) {
            HCMSValidator.paramError();
        }

        String fileName = select.getTitle() + "-附件.zip";
        // 去除不可命名的字符
        char[] nameChars = fileName.toCharArray();
        StringBuilder nameBuilder = new StringBuilder();
        for (char nameChar : nameChars) {
            if (!('/' == nameChar || '\\' == nameChar || '|' == nameChar ||
                    '<' == nameChar || '>' == nameChar || '?' == nameChar ||
                    '*' == nameChar || '"' == nameChar || ':' == nameChar)) {
                nameBuilder.append(nameChar);
            }
        }
        fileName = nameBuilder.toString();

        // 根据浏览器设置编码
        String agent = request.getHeader("User-Agent").toLowerCase();
        if (agent.contains("msie")) {
            fileName = URLEncoder.encode(fileName, "UTF-8");
        } else {
            fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        }
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        response.setHeader("Location", fileName);
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");
        response.setHeader("File-Name", fileName);
        response.setHeader("Access-Control-Expose-Headers", "File-Name");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/zip");
        // 下载服务
        sysArticleDataFilesService.downloadPackageFiles(response.getOutputStream(), id);
    }


}
