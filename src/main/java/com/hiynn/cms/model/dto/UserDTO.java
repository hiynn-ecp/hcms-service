package com.hiynn.cms.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;


/**
 * 用户数据传输 模型DTO
 *
 * @author 张朋
 * @date 2019/11/14 14:15
 */
@ApiModel(value = "用户传输模型UserDTO")
@Data
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 7039307341223280695L;

    @ApiModelProperty(value = "主键,修改使用")
    @NotNull(message = "id必填", groups = Update.class)
    @Length(message = " id无效 长度1-32", groups = Update.class)
    private String id;

    @ApiModelProperty(value = "用户名,新增必填，禁止任何角色修改")
    @NotNull(message = "account 必填", groups = Insert.class)
    @Null(groups = Update.class,message = "禁止修改账户")
    @Length(min = 1, max = 50, message = "account长度1-50", groups = {Insert.class, Update.class})
    private String account;

    @ApiModelProperty(value = "密码,只有自己登录后可以修改，无需验证后台自动校验权限")
    @NotNull(message = "password 必填", groups = Insert.class)
    @Length(min = 1, max = 50, message = "password长度1-50", groups = {Insert.class, Update.class})
    private String password;

    @ApiModelProperty(value = "用户姓名,新增/修改")
    @Length(min = 1, max = 50, message = "userName长度1-50", groups = {Insert.class, Update.class})
    private String userName;

    @ApiModelProperty(value = "手机,新增/修改")
    @Length(min = 1, max = 50, message = "phone长度1-50", groups = {Insert.class, Update.class})
    private String phone;

    @ApiModelProperty(value = "邮箱,新增/修改")
    @Length(min = 1, max = 50, message = "email长度1-50", groups = {Insert.class, Update.class})
    private String email;


}
