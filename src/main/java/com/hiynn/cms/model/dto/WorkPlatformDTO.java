package com.hiynn.cms.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;


/**
 * 工作平台
 *
 * @author 张朋
 * @date 2019-11-12 18:32:51
 */

@ApiModel(value = "工作平台传输模型")
@Data
public class WorkPlatformDTO implements Serializable {

    private static final long serialVersionUID = -6076266964991733440L;
    @ApiModelProperty(value = "主键ID,String")
    @NotBlank(message = "id必填", groups = Update.class)
    @Length(min = 1, max = 32, message = "id长度1-32", groups = {Update.class})
    private String id;

    @ApiModelProperty(value = "平台标题,String")
    @NotBlank(message = "title为空", groups = {Insert.class})
    @Length(min = 1, max = 50, message = "title长度1-50", groups = {Insert.class, Update.class})
    private String title;

    @ApiModelProperty(value = "描述,String")
    @NotBlank(message = "描述为空", groups = {Insert.class})
    @Length(min = 1, max = 500, message = "描述长度1-500", groups = {Insert.class, Update.class})
    private String description;

    @ApiModelProperty(value = "url,String")
    @NotBlank(message = "url为空", groups = {Insert.class})
    @Length(min = 1, max = 500, message = "url长度1-500", groups = {Insert.class, Update.class})
    private String url;

}
