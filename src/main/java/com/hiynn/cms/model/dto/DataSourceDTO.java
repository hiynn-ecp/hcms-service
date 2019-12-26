package com.hiynn.cms.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author 张朋
 * @date 2019/11/11 17:41
 */
@ApiModel("数据源传输数据模型DataSourceDTO")
@Data
public class DataSourceDTO implements Serializable {
    private static final long serialVersionUID = -7692965838550385689L;

    @ApiModelProperty("主键ID,修改时填写")
    @NotNull(groups = Update.class,message = "id必填")
    @Length(groups = Update.class,min = 1,max = 32,message = "id无效")
    private String id;

    @NotNull(groups = Insert.class)
    @Length(groups = {Insert.class,Update.class})
    @ApiModelProperty("数据库-名称")
    private String name;

    @NotNull(groups = Insert.class)
    @Length(groups = {Insert.class,Update.class})
    @ApiModelProperty("数据库-JDBC连接URL")
    private String url;

    @NotNull(groups = Insert.class)
    @Length(groups = {Insert.class,Update.class})
    @ApiModelProperty("数据库-用户名")
    private String userName;

    @NotNull(groups = Insert.class)
    @Length(groups = {Insert.class,Update.class})
    @ApiModelProperty("数据库-密码")
    private String password;

    @NotNull(message = "附件删除状态不为空",groups = Update.class)
    @ApiModelProperty("附件删除状态 0未更改附件 1删除附件")
    private Integer deleteType;

}
