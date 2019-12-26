package com.hiynn.cms.service;

import com.github.pagehelper.PageInfo;
import com.hiynn.cms.entity.SysLogEntity;

import java.util.Date;

/**
 * 系统日志记录表
 *
 * @author 张朋
 * @date 2019-11-12 11:17:04
 */
public interface SysLogService extends BaseService {

    /**
     * ID查询
     */
    SysLogEntity select(String id);

    /**
     * 分页查询
     */
    PageInfo<?> listPage(String page, String pageSize);


    /**
     * 多条件查询
     * @param pageIndex
     * @param pageSize
     * @param searchKey
     * @param startTime
     * @param endTime
     * @return
     */
    PageInfo<?> selectForList(int pageIndex, int pageSize, String searchKey, Date startTime, Date endTime);

    /**
     * 保存
     */
    int insert(SysLogEntity sysLog);

}
