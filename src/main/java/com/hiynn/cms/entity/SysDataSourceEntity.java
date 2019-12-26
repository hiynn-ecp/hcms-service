package com.hiynn.cms.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hiynn.cms.common.util.IDUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 数据源信息实体
 *
 * @author 张朋
 * @date 2019/11/6 16:04
 */
@Data
@ApiModel("数据源信息实体-SysDataSourceEntity")
@JsonIgnoreProperties({"createTime",
        "creatorId",
        "editTime",
        "editorId",
        "dataStatus"})
public class SysDataSourceEntity {

    @ApiModelProperty("主键ID")
    private String id;
    @ApiModelProperty("名称")
    private String name;

    /**
     * 以下参数不可以显示
     */
    private String url;
    private String userName;
    private String password;
    private Date createTime;
    private String creatorId;
    private Date editTime;
    private String editorId;
    private Integer dataStatus;


    /**
     * 复用函数 新增初始化
     *
     * @param userId
     * @return com.hiynn.cms.entity.SysDataSourceEntity
     * @author 张朋
     * @date 2019/11/11 14:27
     */
    public SysDataSourceEntity insert(String userId) {
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
     * @return com.hiynn.cms.entity.SysDataSourceEntity
     * @author 张朋
     * @date 2019/11/11 14:27
     */
    public SysDataSourceEntity update(String userId) {
        Date nowTime = new Date();
        // 补全必要参数
        this.setEditTime(nowTime);
        this.setEditorId(userId);
        return this;
    }
}