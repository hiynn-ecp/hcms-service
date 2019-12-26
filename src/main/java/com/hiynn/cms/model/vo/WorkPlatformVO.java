package com.hiynn.cms.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hiynn.cms.entity.SysWorkPlatformEntity;
import io.swagger.annotations.ApiModel;
import lombok.ToString;


/**
 * 工作平台
 *
 * @author 张朋
 * @date 2019-11-12 18:32:51
 */
@JsonIgnoreProperties(value = {
        "createTime", "editTime",
        "creatorId", "editorId",
        "dataStatus"
})
@ApiModel(value = "工作平台视图模型")
@ToString
public class WorkPlatformVO extends SysWorkPlatformEntity {
    private static final long serialVersionUID = 3768722076405116843L;
}
