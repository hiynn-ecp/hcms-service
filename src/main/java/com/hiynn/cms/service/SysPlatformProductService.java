package com.hiynn.cms.service;

import com.hiynn.cms.entity.SysPlatformProductEntity;
import com.github.pagehelper.PageInfo;

/**
 * 平台产品
 *
 * @author 张朋
 * @date 2019-11-12 18:32:50
 */
public interface SysPlatformProductService extends BaseService {

    /**
     * ID查询
     */
     SysPlatformProductEntity select(String id);

    /**
     * 分页查询
     */
    PageInfo<SysPlatformProductEntity> listPage(Integer page, Integer pageSize);

    /**
     * 查询总数
     */
    int countTotal();

    /**
     * 保存
     */
    int insert(SysPlatformProductEntity sysPlatformProduct);

    /**
     * 更新
     */
    int update(SysPlatformProductEntity sysPlatformProduct);

    /**
     * ID删除
     */
    int delete(String id);

}
