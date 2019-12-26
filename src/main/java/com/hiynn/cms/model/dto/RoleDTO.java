package com.hiynn.cms.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;


/**
 * 角色表
 *
 * @author 张朋
 * @date 2019-10-24 17:19:53
 */
@ApiModel(value = "角色传输模型RoleDTO")
@Data
public class RoleDTO implements Serializable {

    private static final long serialVersionUID = 7243773866538963021L;

    @ApiModelProperty(value = "主键ID,String")
    @NotNull(message = "id必填", groups = Update.class)
    @Length(message = "id无效，长度1-32", min = 1, max = 32, groups = Update.class)
    private String id;

    @ApiModelProperty(value = "角色名称,String")
    @NotNull(message = "name必填", groups = {Insert.class, Update.class})
    @Length(message = "name无效,长度1-50", min = 1, max = 50, groups = {Insert.class, Update.class})
    private String name;
}
