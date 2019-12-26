package com.hiynn.cms.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 系统日志记录表
 *
 * @author 张朋
 * @date 2019-11-12 11:17:04
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "系统日志记录表")
@Data
public class SysLogEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键,String")
    private String id;
    @ApiModelProperty(value = "用户登录名,String")
    private String userAccount;
    @ApiModelProperty(value = "用户姓名,String")
    private String userName;
    @ApiModelProperty(value = "产品名称/业务名称,String")
    private String product;
    @ApiModelProperty(value = "操作描述,String")
    private String operation;
    @ApiModelProperty(value = "方法全路径包括包名,String")
    private String method;
    @ApiModelProperty(value = "接受参数,String")
    private String params;
    @ApiModelProperty(value = "请求ip,String")
    private String ip;
    @ApiModelProperty(value = "归属地,String")
    private String attribution;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @JsonIgnore
    private String creatorId;

}
