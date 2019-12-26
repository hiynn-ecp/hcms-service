package com.hiynn.cms.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * 文章资料 带附件 分类查询 可存储一切文件
 *
 * @author 张朋
 * @date 2019-11-14 18:36:07
 */
@ApiModel(value = "文章资料传输模型")
@Data
public class ArticleDataDTO implements Serializable {

    private static final long serialVersionUID = -9114794821554803759L;

    @ApiModelProperty(value = "主键ID,修改使用")
    @Length(min = 1, max = 32, message = "id无效", groups = Update.class)
    private String id;

    @ApiModelProperty(value = "文章资料标题")
    @NotNull(message = "标题为空", groups = {Insert.class, Update.class})
    @Length(message = "标题无效,长度1-100", min = 1, max = 100, groups = {Insert.class, Update.class})
    private String title;

    @ApiModelProperty(value = "类型")
    @NotNull(message = "类型为空", groups = {Insert.class, Update.class})
    private Integer type;

    @ApiModelProperty(value = "描述")
    @NotNull(message = "描述为空", groups = {Insert.class, Update.class})
    @Length(message = "描述无效 长度 1 - 1000", min = 1, max = 1000, groups = {Insert.class, Update.class})
    private String description;

}
