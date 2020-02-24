package com.hiynn.cms.model.vo;

import com.hiynn.cms.entity.SysStaticDataBackupsEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Auther: liuhy
 * @Date: 2019/12/24 15:54
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BackupsVO extends SysStaticDataBackupsEntity {
    @ApiModelProperty(value = "创建人")
    private String creator;
    @ApiModelProperty(value = "重置人")
    private String recover;
}
