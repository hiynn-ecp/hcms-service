package com.hiynn.cms.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;



/**
 * 描述: 数据源sql文件表
 *
 * @author hiynn
 * @date 2019-12-12 14:49:02
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "数据源sql文件表")
@Data
public class SysDataSourceFilesEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID,String")
    private String id;
    @ApiModelProperty(value = "数据源id,String")
    private String sysDataSourceId;
    @ApiModelProperty(value = "原文件名称,String")
    private String sourceName;
    @ApiModelProperty(value = "新文件名称UUID 库中唯一,String")
    private String targetName;
    @ApiModelProperty(value = "创建时间,Date")
    private Date createTime;
    @ApiModelProperty(value = "创建人id,String")
    private String creatorId;
    
}
