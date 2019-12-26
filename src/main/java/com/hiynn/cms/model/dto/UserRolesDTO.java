package com.hiynn.cms.model.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

/**
 * @author 张朋
 * @date 2019/11/4 16:29
 */
@ApiModel("用户角色分配传输模型-UserRolesDTO")
@Data
public class UserRolesDTO implements Serializable {
    private static final long serialVersionUID = 894796176705464697L;

    @ApiModelProperty("用户id")
    @Length(message = "userId无效,长度1-32",groups =Insert.class,min = 1,max=32)
    private String userId;

    @ApiModelProperty("角色id集合")
    private Set<String> roles;

}
