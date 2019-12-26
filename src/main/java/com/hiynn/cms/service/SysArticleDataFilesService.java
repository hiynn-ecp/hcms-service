package com.hiynn.cms.service;

import com.hiynn.cms.entity.SysArticleDataFilesEntity;

import java.io.OutputStream;
import java.util.List;

/**
 * 文章资料附件表
 *
 * @author 张朋
 * @date 2019-11-15 10:47:57
 */
public interface SysArticleDataFilesService extends BaseService {

    /**
     * 根据ID查询一个文件信息 目前是后台删除的时候使用
     *
     * @param id
     * @return com.hiynn.cms.entity.SysArticleDataFilesEntity
     * @author 张朋
     * @date 2019/11/15 14:00
     */
    SysArticleDataFilesEntity select(String id);

    /**
     * 根据ID删除文件 与 数据库数据
     *
     * @param id
     * @return int
     * @author 张朋
     * @date 2019/11/15 13:59
     */
    int delete(String id);


    /**
     * 查询文章的一组文件
     *
     * @param sysArticleDataId
     * @return java.util.List<com.hiynn.cms.entity.SysArticleDataFilesEntity>
     * @author 张朋
     * @date 2019/11/15 14:40
     */
    List<SysArticleDataFilesEntity> listByArticleDataId(String sysArticleDataId);


    /**
     * 打包下载文章资料的附件组
     *
     * @param out
     * @param id 文章资料id
     * @return void
     * @author 张朋
     * @date 2019/11/22 14:32
     */
    void downloadPackageFiles(OutputStream out, String id);




}
