package com.hiynn.cms.dao;

import com.hiynn.cms.entity.SysLogEntity;
import com.hiynn.component.common.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

/**
 * 系统日志记录表
 *
 * @author 张朋
 * @date 2019-11-12 11:17:04
 */
@Mapper
public interface SysLogMapper extends BaseMapper<SysLogEntity> {

    /**
     * 多条件查询
     *
     * @param searchKey
     * @param startTime
     * @param endTime
     * @return
     */
    List<SysLogEntity> selectForList(String searchKey, Date startTime, Date endTime);

}
