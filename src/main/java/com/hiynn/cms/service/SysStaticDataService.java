package com.hiynn.cms.service;

import com.hiynn.cms.entity.SysStaticDataEntity;
import com.github.pagehelper.PageInfo;

/**
 * 描述: 静态资源表(只保存静态资源路径)
 *
 * @author liuhy
 * @date 2019-12-24 10:54:07
 */
public interface SysStaticDataService extends BaseService{

    /**
     * 描述: ID查询
     * @author liuhy
     * @date 2019-12-24 10:54:07
     * @param id
     * @return SysStaticDataEntity
     */
    SysStaticDataEntity select(String id);

    /**
     * 描述: 分页查询
     * @author liuhy
     * @date 2019-12-24 10:54:07
     * @param page
     * @param pageSize
     * @return PageInfo
     */
    PageInfo<?> listPage(String page, String pageSize);

    /**
     * 描述: 查询总数
     * @author liuhy
     * @date 2019-12-24 10:54:07
     * @param
     * @return int
     */
    int countTotal();

    /**
     * 描述: 保存
     * @author liuhy
     * @date 2019-12-24 10:54:07
     * @param sysStaticData
     * @return int
     */
    int insert(SysStaticDataEntity sysStaticData);

    /**
     * 描述: 更新
     * @author liuhy
     * @date 2019-12-24 10:54:07
     * @param sysStaticData
     * @return int
     */
    int update(SysStaticDataEntity sysStaticData);

    /**
     * 描述: ID删除
     * @author liuhy
     * @date 2019-12-24 10:54:07
     * @param id
     * @return int
     */
    int delete(String id);

}
