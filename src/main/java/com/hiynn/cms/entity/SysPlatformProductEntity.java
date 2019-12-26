package com.hiynn.cms.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hiynn.cms.common.util.IDUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 平台产品
 *
 * @author 张朋
 * @date 2019-11-12 18:32:50
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "平台产品")
@Data
public class SysPlatformProductEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID,String")
    private String id;
    @ApiModelProperty(value = "产品标题,String")
    private String title;
    @ApiModelProperty(value = "产品内容,String")
    private String content;


    private Integer dataStatus;
    private Date createTime;
    private String creatorId;
    private Date editTime;
    private String editorId;

    /**
     * 复用函数 新增初始化
     *
     * @param userId
     * @return com.hiynn.cms.entity.SysPlatformProductEntity
     * @author 张朋
     * @date 2019/11/11 14:27
     */
    public SysPlatformProductEntity insert(String userId) {
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
     * @return com.hiynn.cms.entity.SysPlatformProductEntity
     * @author 张朋
     * @date 2019/11/11 14:27
     */
    public SysPlatformProductEntity update(String userId) {
        Date nowTime = new Date();
        // 补全必要参数
        this.setEditTime(nowTime);
        this.setEditorId(userId);
        return this;
    }


}
