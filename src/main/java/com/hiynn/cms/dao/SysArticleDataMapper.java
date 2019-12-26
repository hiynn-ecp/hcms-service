package com.hiynn.cms.dao;

import com.hiynn.cms.entity.SysArticleDataEntity;
import com.hiynn.component.common.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文章资料 带附件 分类查询 可存储一切文件
 *
 * @author 张朋
 * @date 2019-11-14 18:36:07
 */
@Mapper
public interface SysArticleDataMapper extends BaseMapper<SysArticleDataEntity> {


    /**
     * 根据 类型或者 标题 查询 类型必填 标题 可选
     *
     * @param type
     * @param title
     * @return
     */
    List<SysArticleDataEntity> listByTypeOrTitle(@Param("type") Integer type, @Param("title") String title);

}
