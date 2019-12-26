package com.hiynn.cms.dao;

import com.hiynn.cms.entity.SysDataSourceEntity;
import com.hiynn.cms.model.vo.DataSourceVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysDataSourceEntityMapper {

    int deleteByPrimaryKey(String id);

    int insertSelective(SysDataSourceEntity record);

    SysDataSourceEntity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysDataSourceEntity record);

    /**
     * 查询所有配置的数据源
     * @return
     */
    List<DataSourceVO> list();





}