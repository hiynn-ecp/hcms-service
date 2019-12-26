package com.hiynn.cms.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hiynn.cms.common.util.IDUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 角色表
 *
 * @author 张朋
 * @date 2019-10-24 17:19:53
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "角色实体模型")
@Data
public class SysRoleEntity implements Serializable {

    private static final long serialVersionUID = 7603476205695694586L;

    @ApiModelProperty(value = "主键ID,String")
    private String id;
    @ApiModelProperty(value = "角色名称,String")
    private String name;

    @JsonIgnore
    private Date createTime;
    @JsonIgnore
    private String creatorId;
    @JsonIgnore
    private Date editTime;
    @JsonIgnore
    private String editorId;
    @JsonIgnore
    private Integer dataStatus;


    /**
     * 复用函数 新增初始化
     *
     * @param userId
     * @return com.hiynn.cms.entity.SysRoleEntity
     * @author 张朋
     * @date 2019/11/11 14:27
     */
    public SysRoleEntity insert(String userId) {
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
     * @return com.hiynn.cms.entity.SysRoleEntity
     * @author 张朋
     * @date 2019/11/11 14:27
     */
    public SysRoleEntity update(String userId) {
        Date nowTime = new Date();
        // 补全必要参数
        this.setEditTime(nowTime);
        this.setEditorId(userId);
        return this;
    }


}
