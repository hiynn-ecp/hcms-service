package com.hiynn.cms.dao;

import com.hiynn.cms.entity.SysDataSourceFilesEntity;
import com.hiynn.component.common.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 描述: 数据源sql文件表
 *
 * @author hiynn
 * @date 2019-12-12 14:49:02
 */
@Mapper
public interface SysDataSourceFilesMapper extends BaseMapper<SysDataSourceFilesEntity> {

    /**
     * 描述: 根据数据Id查询 数据源sql文件
     * @author liuhy
     * @date 2019/12/12 15:23
     * @param dataSourceId
     * @return java.util.List<com.hiynn.cms.entity.SysDataSourceFilesEntity>
     */
    SysDataSourceFilesEntity selectFilesByDataSourceId(@Param("dataSourceId") String dataSourceId);

}
