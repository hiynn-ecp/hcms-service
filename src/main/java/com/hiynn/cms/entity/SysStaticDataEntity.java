package com.hiynn.cms.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hiynn.cms.common.util.IDUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;



/**
 * 描述: 静态资源表(只保存静态资源路径)
 *
 * @author liuhy
 * @date 2019-12-24 15:18:03
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "静态资源表(只保存静态资源路径)")
@Data
public class SysStaticDataEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = ",String")
    private String id;
    @ApiModelProperty(value = "静态资源名称,String")
    private String name;
    @ApiModelProperty(value = "静态资源路径,String")
    private String path;
    @ApiModelProperty(value = "创建时间,Date")
    private Date createTime;
    @ApiModelProperty(value = "创建人id,String")
    private String creatorId;
    @ApiModelProperty(value = "更新时间,Date")
    private Date editTime;
    @ApiModelProperty(value = "修改人id,String")
    private String editorId;
    @ApiModelProperty(value = "1:可用 0：不可用,Integer")
    private Integer dataStatus;
    /**
     * 复用函数 新增初始化
     *
     * @param userId
     * @return com.hiynn.cms.entity.SysMenuEntity
     * @author 张朋
     * @date 2019/11/11 14:27
     */
    public SysStaticDataEntity insert(String userId) {
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
    public SysStaticDataEntity update(String userId) {
        Date nowTime = new Date();
        // 补全必要参数
        this.setEditTime(nowTime);
        this.setEditorId(userId);
        return this;
    }
}
