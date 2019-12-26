package com.hiynn.cms.dao;

import com.hiynn.cms.entity.SysStaticDataEntity;
import com.hiynn.cms.model.vo.StaticDataVO;
import com.hiynn.component.common.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 描述: 静态资源表(只保存静态资源路径)
 *
 * @author liuhy
 * @date 2019-12-24 15:18:03
 */
@Mapper
public interface SysStaticDataMapper extends BaseMapper<SysStaticDataEntity> {

    /**
     * 描述: 列表查询
     * @author liuhy
     * @date 2019/12/24 16:04
     * @param
     * @return java.util.List<com.hiynn.cms.model.vo.StaticDataVO>
     */
    List<StaticDataVO> listPage();
}
