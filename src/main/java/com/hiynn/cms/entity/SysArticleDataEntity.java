package com.hiynn.cms.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hiynn.cms.common.util.IDUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 文章资料 带附件 分类查询 可存储一切文件
 *
 * @author 张朋
 * @date 2019-11-14 18:36:07
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "文章资料模型")
@Data
public class SysArticleDataEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID,String")
    private String id;
    @ApiModelProperty(value = "文章资料标题,String")
    private String title;
    @ApiModelProperty(value = "类型,Integer")
    private Integer type;
    @ApiModelProperty(value = "描述 长度1000,String")
    private String description;

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
     * @return com.hiynn.cms.entity.SysArticleDataEntity
     * @author 张朋
     * @date 2019/11/11 14:27
     */
    public SysArticleDataEntity insert(String userId) {
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
     * @return com.hiynn.cms.entity.SysArticleDataEntity
     * @author 张朋
     * @date 2019/11/11 14:27
     */
    public SysArticleDataEntity update(String userId) {
        Date nowTime = new Date();
        // 补全必要参数
        this.setEditTime(nowTime);
        this.setEditorId(userId);
        return this;
    }

}
