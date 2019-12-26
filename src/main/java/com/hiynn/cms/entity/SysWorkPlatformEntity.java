package com.hiynn.cms.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hiynn.cms.common.util.IDUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 工作平台
 *
 * @author 张朋
 * @date 2019-11-12 18:32:51
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "工作平台")
@Data
public class SysWorkPlatformEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID,String")
    private String id;
    @ApiModelProperty(value = "平台标题,String")
    private String title;
    @ApiModelProperty(value = "平台图标,String")
    private String icon;
    @ApiModelProperty(value = "描述,String")
    private String description;
    @ApiModelProperty(value = "url,String")
    private String url;

    @ApiModelProperty(value = "创建时间,Date")
    private Date createTime;
    @ApiModelProperty(value = "创建人ID,String")
    private String creatorId;
    @ApiModelProperty(value = "修改时间,Date")
    private Date editTime;
    @ApiModelProperty(value = "修改人ID,String")
    private String editorId;
    @ApiModelProperty(value = "数据有效性 1有效 0无效,Integer")
    private Integer dataStatus;

    /**
     * 复用函数 新增初始化
     *
     * @param userId
     * @return com.hiynn.cms.entity.SysWorkPlatformEntity
     * @author 张朋
     * @date 2019/11/11 14:27
     */
    public SysWorkPlatformEntity insert(String userId) {
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
     * @return com.hiynn.cms.entity.SysWorkPlatformEntity
     * @author 张朋
     * @date 2019/11/11 14:27
     */
    public SysWorkPlatformEntity update(String userId) {
        Date nowTime = new Date();
        // 补全必要参数
        this.setEditTime(nowTime);
        this.setEditorId(userId);
        return this;
    }

}
