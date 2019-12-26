package com.hiynn.cms.service;

import com.github.pagehelper.PageInfo;
import com.hiynn.cms.entity.SysArticleDataEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;

/**
 * 文章资料 带附件 分类查询 可存储一切文件
 *
 * @author 张朋
 * @date 2019-11-12 18:32:50
 */
public interface SysArticleDataService extends BaseService {

    /**
     * ID查询
     */
    SysArticleDataEntity select(String id);

    /**
     * 分页查询
     */
    PageInfo<SysArticleDataEntity> listPage(Integer page, Integer pageSize);


    /**
     * 分页查询 根据类型 与 标题
     *
     * @param page
     * @param pageSize
     * @param type
     * @param title
     * @return com.github.pagehelper.PageInfo<com.hiynn.cms.entity.SysArticleDataEntity>
     * @author 张朋
     * @date 2019/11/14 18:49
     */
    PageInfo<SysArticleDataEntity> listByTypeOrTitle(Integer page, Integer pageSize, Integer type, String title);


    /**
     * 查询总数
     */
    int countTotal();

    /**
     * 保存
     */
    int insert(SysArticleDataEntity sysArticleData, MultipartFile[] files);

    /**
     * 更新
     */
    int update(SysArticleDataEntity sysArticleData, MultipartFile[] files);

    /**
     * ID删除
     */
    int delete(String id);


}
