package com.hiynn.cms.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hiynn.cms.entity.SysMenuEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 菜单/产品/按钮/返回模型
 *
 * @author 张朋
 * @date 2019/10/30 16:06
 */
@JsonIgnoreProperties(value = {
        "createTime", "editTime",
        "creatorId", "editorId",
        "dataStatus"
})
@ToString
@ApiModel(value = "菜单视图模型-MenuVO")
public class MenuVO extends SysMenuEntity {

    private static final long serialVersionUID = 7725348836765460167L;
    /**
     * 子菜单
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Getter
    @Setter
    @ApiModelProperty("子菜单")
    List<MenuVO> child;
}
