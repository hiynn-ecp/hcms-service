package com.hiynn.cms.service;

import com.hiynn.cms.entity.SysWorkPlatformEntity;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Insert;
import org.springframework.web.multipart.MultipartFile;

/**
 * 工作平台
 *
 * @author 张朋
 * @date 2019-11-12 18:32:51
 */
public interface SysWorkPlatformService extends BaseService {

    /**
     * ID查询
     */
     SysWorkPlatformEntity select(String id);

    /**
     * 分页查询
     */
    PageInfo<SysWorkPlatformEntity> listPage(Integer page, Integer pageSize);

    /**
     * 查询总数
     */
    int countTotal();

    /**
     * 保存
     */
    int insert(SysWorkPlatformEntity sysWorkPlatform, MultipartFile icon);

    /**
     * 更新
     */
    int update(SysWorkPlatformEntity sysWorkPlatform,MultipartFile icon);

    /**
     * ID删除
     */
    int delete(String id);

}
