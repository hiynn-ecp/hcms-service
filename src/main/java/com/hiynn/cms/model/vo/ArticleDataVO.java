package com.hiynn.cms.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hiynn.cms.entity.SysArticleDataEntity;
import io.swagger.annotations.ApiModel;
import lombok.ToString;


/**
 * 文章资料 视图模型
 *
 * @author 张朋
 * @date 2019-11-12 18:32:50
 */

@ApiModel(value = "文章资料视图模型")
@JsonIgnoreProperties(value = {
        "editTime",
        "creatorId", "editorId",
        "dataStatus"
})
@ToString
public class ArticleDataVO extends SysArticleDataEntity {

    private static final long serialVersionUID = 7072755617276070334L;
}
