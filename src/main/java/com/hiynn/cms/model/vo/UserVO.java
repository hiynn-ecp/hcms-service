package com.hiynn.cms.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hiynn.cms.entity.SysUserEntity;
import io.swagger.annotations.ApiModel;
import lombok.ToString;

/**
 * @author 张朋
 * @date 2019/10/30 14:38
 */
@JsonIgnoreProperties(value = {
        "password", "salt",
        "createTime", "editTime",
        "creatorId", "editorId",
        "dataStatus"
})
@ToString
@ApiModel("用户视图模型UserVO")
public class UserVO extends SysUserEntity {
    private static final long serialVersionUID = 7301050861229764850L;
}
