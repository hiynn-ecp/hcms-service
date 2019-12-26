package com.hiynn.cms.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hiynn.cms.common.util.IDUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;


/**
 * 系统枚举表
 *
 * @author 张朋
 * @date 2019-11-11 10:48:04
 */
@Data
@ApiModel("枚举表实体模型")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysEnumEntity implements Serializable {

    private static final long serialVersionUID = 3238298298184895457L;


    @ApiModelProperty(value = "主键,String,修改必填")
    @NotNull(message = "缺少参数id",groups = Update.class)
    @Length(max = 32,min = 1,message = "id长度1-32",groups = Update.class)
    private String id;

    @ApiModelProperty(value = "枚举key值,Integer")
    @NotNull(message = "缺少参数key",groups = Insert.class)
    @Max(value = 99,message = "key值最大99",groups = {Insert.class,Update.class})
    private Integer key;

    @ApiModelProperty(value = "枚举名称,String")
    @NotNull(message = "缺少参数value",groups = Insert.class)
    @Length(max = 100,min = 1,message = "value长度1-100",groups = {Insert.class,Update.class})
    private String value;

    @ApiModelProperty(value = "类别,String")
    @NotNull(message = "缺少参数type",groups = Insert.class)
    @Length(max = 100,min = 1,message = "type长度1-100",groups = {Insert.class,Update.class})
    private String type;

    @ApiModelProperty(value = "描述,String")
    @Length(max = 200,min = 1,message = "description长度1-200",groups = {Insert.class,Update.class})
    private String description;

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
     * @return com.hiynn.cms.entity.SysEnumEntity
     * @author 张朋
     * @date 2019/11/11 14:27
     */
    public SysEnumEntity insert(String userId) {
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
     * @return com.hiynn.cms.entity.SysEnumEntity
     * @author 张朋
     * @date 2019/11/11 14:27
     */
    public SysEnumEntity update(String userId) {
        Date nowTime = new Date();
        // 补全必要参数
        this.setEditTime(nowTime);
        this.setEditorId(userId);
        return this;
    }


}
