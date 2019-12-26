package com.hiynn.cms.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

/**
 * @author 张朋
 * @date 2019/11/4 17:02
 */
@ApiModel("角色菜单分配传输模型-RoleMenusDTO")
@Data
public class RoleMenusDTO implements Serializable {
    private static final long serialVersionUID = 6180087953209972346L;
    @ApiModelProperty(value = "角色ID", required = true)
    @NotNull(message = "角色ID无效，isNull")
    @Length(min = 1,max = 32,message = "角色ID无效，长度1-32")
    private String roleId;

    @ApiModelProperty(value = "菜单id集合", required = true)
    private Set<String> menuIds;

}
