package com.hiynn.cms.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;



/**
 * 平台产品传输模型
 *
 * @author 张朋
 * @date 2019/11/13 16:06
 */
@ApiModel(value = "平台产品传输模型-PlatformProductVO")
@Data
public class PlatformProductDTO implements Serializable {

    private static final long serialVersionUID = 7674852697401677903L;

    @ApiModelProperty(value = "主键ID,String")
    private String id;
    @ApiModelProperty(value = "产品标题,String")
    private String title;
    @ApiModelProperty(value = "产品内容,String")
    private String content;

}
