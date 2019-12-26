package com.hiynn.cms.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 用户角色关联表
 *
 * @author 张朋
 * @date 2019-10-24 17:19:53
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "用户角色关联表")
@Data
public class SysUserRoleREntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID,String")
    private String id;
    @ApiModelProperty(value = "用户ID,String")
    private String userId;
    @ApiModelProperty(value = "角色ID,String")
    private String roleId;
    @ApiModelProperty(value = "创建时间,Date")
    private Date createTime;
    @ApiModelProperty(value = "数据有效性 1有效 0无效,Integer")
    private Integer dataStatus;

}
