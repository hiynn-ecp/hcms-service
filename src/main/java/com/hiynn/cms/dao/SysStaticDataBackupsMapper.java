package com.hiynn.cms.dao;

import com.hiynn.cms.entity.SysStaticDataBackupsEntity;
import com.hiynn.cms.model.vo.BackupsVO;
import com.hiynn.component.common.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述: 静态资源备份表
 *
 * @author liuhy
 * @date 2019-12-24 15:18:03
 */
@Mapper
public interface SysStaticDataBackupsMapper extends BaseMapper<SysStaticDataBackupsEntity> {
    /**
     * 描述: 查询列表
     * @author liuhy
     * @date 2019/12/24 16:03
     * @param
     * @return java.util.List<com.hiynn.cms.model.vo.BackupsVO>
     */
    List<BackupsVO> listPage(@Param("sysStaticDataId") String sysStaticDataId);
}
