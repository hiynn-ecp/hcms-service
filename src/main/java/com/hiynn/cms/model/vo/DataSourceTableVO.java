package com.hiynn.cms.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 据表视图模型
 * @author 张朋
 * @date 2019/11/6 15:56
 */

@Data
@ApiModel("数据表视图模型-DataSourceTableVO")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataSourceTableVO implements Serializable {

    private static final long serialVersionUID = -8151120914521552112L;
    @ApiModelProperty("数据表名称")
    private String tableName;
    @ApiModelProperty("类型")
    private String engine;
    @ApiModelProperty("备注")
    private String tableComment;
    @ApiModelProperty("创建时间")
    private Date createTime;

}
