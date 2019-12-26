package com.hiynn.cms.dao;

import com.hiynn.cms.entity.SysEnumEntity;
import com.hiynn.component.common.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统枚举表
 *
 * @author 张朋
 * @date 2019-11-11 10:48:04
 */
@Mapper
public interface SysEnumMapper extends BaseMapper<SysEnumEntity> {


    /**
     * 根据枚举类型查询一组数据
     *
     * @param type
     * @return java.util.List<com.hiynn.cms.entity.SysEnumEntity>
     * @author 张朋
     * @date 2019/11/12 18:06
     */
    List<SysEnumEntity> listByType(String type);

    /**
     * 分页查询 或者模糊分页查询某类型的
     *
     * @param type
     * @return java.util.List<com.hiynn.cms.entity.SysEnumEntity>
     * @author 张朋
     * @date 2019/11/14 11:13
     */
    List<SysEnumEntity> listPageOrByType(@Param("type") String type);
}
