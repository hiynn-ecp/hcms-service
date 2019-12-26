package com.hiynn.cms.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 文章资料 带附件 分类查询 可存储一切文件
 *
 * @author 张朋
 * @date 2019-11-14 18:58:13
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "文章资料附件模型")
@Data
public class SysArticleDataFilesEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID,String")
    private String id;
    @ApiModelProperty(value = "文章资料id,String")
    private String sysArticleDataId;
    @ApiModelProperty(value = "原文件名称,String")
    private String sourceName;
    @ApiModelProperty(value = "新文件名称UUID 库中唯一,String")
    private String targetName;
    @ApiModelProperty(value = "新文件名称的PDF预览文件，文档类有效， 库中唯一,String")
    private String previewName;

    @JsonIgnore
    private Date createTime;
    @JsonIgnore
    private String creatorId;

}
