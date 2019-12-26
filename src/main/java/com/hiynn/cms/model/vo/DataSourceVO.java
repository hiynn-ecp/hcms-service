package com.hiynn.cms.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据源信息视图模
 * @author 张朋
 * @date 2019/11/14 14:29
 */
@Data
@ApiModel("数据源信息视图模型-DataSourceVO")
public class DataSourceVO implements Serializable {
    private static final long serialVersionUID = -270175916991625309L;
    @ApiModelProperty("主键ID")
    private String id;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("url")
    private String url;
    @ApiModelProperty("名称+(ip)")
    private String nameIp;
    @ApiModelProperty("数据源用户名")
    private String userName;
    @ApiModelProperty("数据源密码")
    private String password;
    @ApiModelProperty("数据源创建时间")
    private Date createTime;
    @ApiModelProperty("附件原名称")
    private String sourceName;
    @ApiModelProperty("附件原名称")
    private String targetName;
    @ApiModelProperty("附件删除状态 0未更改附件 1删除附件")
    private Integer deleteType;


}