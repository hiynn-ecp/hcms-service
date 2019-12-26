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
 * 用户表
 *
 * @author 张朋
 * @date 2019-10-24 17:19:52
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "用户表")
@Data
public class SysUserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键,修改/删除/单个查询使用")
    private String id;
    @ApiModelProperty(value = "用户名,新增必填，禁止任何角色修改")
    private String account;
    @ApiModelProperty(value = "密码,只有自己登录后可以修改，无需验证后台自动校验权限")
    private String password;
    @JsonIgnore
    private String salt;
    @ApiModelProperty(value = "用户姓名,新增/修改")
    private String userName;
    @ApiModelProperty(value = "手机,新增/修改")
    private String phone;
    @ApiModelProperty(value = "邮箱,新增/修改")
    private String email;
    @ApiModelProperty(value = "用户头像,新增/修改")
    private String picture;

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
     * @return com.hiynn.cms.entity.SysUserEntity
     * @author 张朋
     * @date 2019/11/11 14:27
     */
    public SysUserEntity insert(String userId) {
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
     * @return com.hiynn.cms.entity.SysUserEntity
     * @author 张朋
     * @date 2019/11/11 14:27
     */
    public SysUserEntity update(String userId) {
        Date nowTime = new Date();
        // 补全必要参数
        this.setEditTime(nowTime);
        this.setEditorId(userId);
        return this;
    }


}
