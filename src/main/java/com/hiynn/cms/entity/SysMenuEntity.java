package com.hiynn.cms.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hiynn.cms.common.util.IDUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 目录/菜单/按钮/表
 *
 * @author 张朋
 * @date 2019-10-24 17:19:53
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "目录/菜单/按钮/表")
@Data
public class SysMenuEntity implements Serializable {

    private static final long serialVersionUID = 5509255946158664192L;

    @ApiModelProperty(value = "主键,String")
    private String id;
    @ApiModelProperty(value = "父菜单名称，一级菜单为空串,String")
    private String menuName;
    @ApiModelProperty(value = "父菜单ID，一级菜单为空串,String")
    private String menuId;
    @ApiModelProperty(value = "菜单名称,String")
    private String name;
    @ApiModelProperty(value = "菜单uri type=2 时 有效,String")
    private String uri;
    @ApiModelProperty(value = "授权 类型为 接口或按钮时 有效,String")
    private String perms;
    @ApiModelProperty(value = "枚举类型   1：目录   2：菜单   3：按钮 4：接口,Integer")
    private Integer type;
    @ApiModelProperty(value = "枚举类型名称")
    private String typeName;
    @ApiModelProperty(value = "排序字段,Integer")
    private Integer ordered;

    private Date createTime;
    private String creatorId;
    private Date editTime;
    private String editorId;
    private Integer dataStatus;

    /**
     * 复用函数 新增初始化
     *
     * @param userId
     * @return com.hiynn.cms.entity.SysMenuEntity
     * @author 张朋
     * @date 2019/11/11 14:27
     */
    public SysMenuEntity insert(String userId) {
        update(userId);
        this.setId(IDUtils.getJavaUUID());
        this.setCreateTime(this.getEditTime());
        this.setCreatorId(userId);
        return this;
    }


    /**
     * 复用函数 修改初始化
     *
     * @param userId
     * @return com.hiynn.cms.entity.SysMenuEntity
     * @author 张朋
     * @date 2019/11/11 14:27
     */
    public SysMenuEntity update(String userId) {
        Date nowTime = new Date();
        // 补全必要参数
        this.setEditTime(nowTime);
        this.setEditorId(userId);
        return this;
    }


}
