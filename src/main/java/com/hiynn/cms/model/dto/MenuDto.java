package com.hiynn.cms.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * 目录/菜单/按钮/表
 *
 * @author 张朋
 * @date 2019-10-24 17:19:53
 */
@ApiModel(value = "目录/菜单/按钮-传输模型-MenuDto")
@Data
public class MenuDto implements Serializable {

    private static final long serialVersionUID = -7692965838550385689L;

    @ApiModelProperty(value = "主键,String")
    @NotNull(message = "id修改必填",groups = Update.class)
    @Length(min = 1,max = 32,message = "id无效，长度1-32",groups = {Update.class})
    private String id;

    @ApiModelProperty(value = "父菜单ID，一级菜单为空串,String")
    @Length(max = 32,message = "menuId无效，长度1-32",groups = {Insert.class,Update.class})
    private String menuId;

    @ApiModelProperty(value = "菜单名称,String")
    @NotNull(message = "name is Null" ,groups =Insert.class)
    @Length(min = 1,max = 32,message = "name无效，长度1-50",groups = {Insert.class,Update.class})
    private String name;

    @ApiModelProperty(value = "菜单uri type=2 时 有效,String")
    @Length(min = 1,max = 200,message = "URL无效，长度1-200",groups = {Insert.class,Update.class})
    private String uri;

    @ApiModelProperty(value = "授权 type=2时 有效,String")
    @Length(max = 255,message = "perms无效，长度4-255",groups = {Insert.class,Update.class})
    private String perms;

    @ApiModelProperty(value = "类型   1：目录   2：菜单   3：按钮,Integer")
    @NotNull(message = "type is null",groups = Insert.class)
    @Max(value = 99,message = "类型无效，1-99 整型",groups = {Insert.class,Update.class})
    private Integer type;

    @ApiModelProperty(value = "排序字段,Integer")
    private Integer ordered;
}
