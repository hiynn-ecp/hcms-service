package com.hiynn.cms.service;

import com.github.pagehelper.PageInfo;
import com.hiynn.cms.entity.SysEnumEntity;

import java.util.List;

/**
 * 系统枚举表
 *
 * @author 张朋
 * @date 2019-11-11 10:48:04
 */
public interface SysEnumService extends BaseService {

    /**
     * ID查询
     */
    SysEnumEntity select(String id);

    /**
     * 分页查询
     */
    PageInfo<?> listPage(Integer page, Integer pageSize,String type);

    /**
     * 查询总数
     */
    int countTotal();

    /**
     * 保存
     */
    int insert(SysEnumEntity sysEnum);

    /**
     * 更新
     */
    int update(SysEnumEntity sysEnum);

    /**
     * ID删除
     */
    int delete(String id);

    /**
     * 根据类型查询一组枚举
     * @param type
     * @return
     */
   List<SysEnumEntity> listByType(String type);


}
