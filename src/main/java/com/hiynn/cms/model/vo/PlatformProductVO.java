package com.hiynn.cms.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hiynn.cms.entity.SysPlatformProductEntity;
import io.swagger.annotations.ApiModel;
import lombok.ToString;

/**
 * 平台产品视图模型
 *
 * @author 张朋
 * @date 2019/11/13 16:06
 */
@JsonIgnoreProperties(value = {
        "createTime", "editTime",
        "creatorId", "editorId",
        "dataStatus"
})
@ToString
@ApiModel(value = "平台产品视图模型-PlatformProductVO")
public class PlatformProductVO extends SysPlatformProductEntity {
    private static final long serialVersionUID = -8014826394564054664L;
}
