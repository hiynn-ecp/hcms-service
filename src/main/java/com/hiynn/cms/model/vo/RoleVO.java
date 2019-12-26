package com.hiynn.cms.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hiynn.cms.entity.SysRoleEntity;
import io.swagger.annotations.ApiModel;
import lombok.ToString;

/**
 * @author 张朋
 * @date 2019/10/30 15:23
 */


@JsonIgnoreProperties(value = {
        "createTime", "editTime",
        "creatorId", "editorId",
        "dataStatus"
})
@ToString
@ApiModel("角色视图模型RoleVO")
public class RoleVO extends SysRoleEntity {
    private static final long serialVersionUID = -2561604152436758542L;
}
