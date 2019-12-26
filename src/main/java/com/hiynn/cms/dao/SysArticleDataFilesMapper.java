package com.hiynn.cms.dao;

import com.hiynn.cms.entity.SysArticleDataFilesEntity;
import com.hiynn.component.common.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 文章资料附件表
 *
 * @author 张朋
 * @date 2019-11-15 10:47:57
 */
@Mapper
public interface SysArticleDataFilesMapper extends BaseMapper<SysArticleDataFilesEntity> {


    /**
     * 查询某个文章的所有附件
     *
     * @param articleDataId
     * @return java.util.List<com.hiynn.cms.entity.SysArticleDataFilesEntity>
     * @author 张朋
     * @date 2019/11/15 14:13
     */
    List<SysArticleDataFilesEntity> listByArticleDataId(String articleDataId);

}
