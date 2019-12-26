package com.hiynn.cms.service;

import com.hiynn.cms.entity.SysStaticDataBackupsEntity;
import com.github.pagehelper.PageInfo;

/**
 * 描述: 静态资源备份表
 *
 * @author liuhy
 * @date 2019-12-24 10:54:07
 */
public interface SysStaticDataBackupsService extends BaseService{

    /**
     * 描述: ID查询
     * @author liuhy
     * @date 2019-12-24 10:54:07
     * @param id
     * @return SysStaticDataBackupsEntity
     */
    SysStaticDataBackupsEntity select(String id);

    /**
     * 描述: 分页查询
     * @author liuhy
     * @date 2019-12-24 10:54:07
     * @param page
     * @param pageSize
     * @return PageInfo
     */
    PageInfo<?> listPage(String page, String pageSize,String sysStaticDataId);

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
     * @param staticDataId
     * @return int
     */
    int insert(String staticDataId);

    /**
     * 描述: ID删除
     * @author liuhy
     * @date 2019-12-24 10:54:07
     * @param id
     * @return int
     */
    int delete(String id);

    /**
     * 描述: 恢复静态文件
     * @author liuhy
     * @date 2019/12/24 14:45
     * @param id
     * @return void
     */
    void recover(String id);

    /**
     * 描述: 更新资源备份
     * @author liuhy
     * @date 2019/12/24 14:45
     * @param id
     * @return void
     */
    void update(String id,String remark);

}
