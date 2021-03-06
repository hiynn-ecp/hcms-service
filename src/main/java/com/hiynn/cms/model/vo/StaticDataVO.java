package com.hiynn.cms.model.vo;

import com.hiynn.cms.entity.SysStaticDataEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Auther: liuhy
 * @Date: 2019/12/24 15:49
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class StaticDataVO extends SysStaticDataEntity {
    @ApiModelProperty(value = "创建人")
    private String creator;
    @ApiModelProperty(value = "修改人")
    private String editor;
}
