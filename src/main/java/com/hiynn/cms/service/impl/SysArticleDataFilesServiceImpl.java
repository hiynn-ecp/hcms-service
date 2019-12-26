package com.hiynn.cms.service.impl;

import com.hiynn.cms.common.config.WebStaticLocationConfig;
import com.hiynn.cms.common.exception.ResultException;
import com.hiynn.cms.common.util.FileUtils;
import com.hiynn.cms.dao.SysArticleDataFilesMapper;
import com.hiynn.cms.entity.SysArticleDataFilesEntity;
import com.hiynn.cms.service.SysArticleDataFilesService;
import com.hiynn.component.common.core.Result;
import com.hiynn.component.common.util.ZipUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.ZipOutputStream;

/**
 * 文章资料附件表
 *
 * @author 张朋
 * @date 2019-11-15 10:47:57
 */
@Service
@Slf4j
public class SysArticleDataFilesServiceImpl implements SysArticleDataFilesService {

    private final SysArticleDataFilesMapper sysArticleDataFilesMapper;
    private final WebStaticLocationConfig staticLocationConfig;

    @Autowired
    public SysArticleDataFilesServiceImpl(SysArticleDataFilesMapper sysArticleDataFilesMapper,
                                          WebStaticLocationConfig staticLocationConfig) {
        this.sysArticleDataFilesMapper = sysArticleDataFilesMapper;
        this.staticLocationConfig = staticLocationConfig;
    }

    @Override
    public SysArticleDataFilesEntity select(String id) {
        return sysArticleDataFilesMapper.select(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int delete(String id) {
        SysArticleDataFilesEntity select = select(id);
        if (select == null) {
            return 0;
        }
        // 删除文件、数据
        int delete = sysArticleDataFilesMapper.delete(id);
        if (delete == 0) {
            return 0;
        }
        // 删除对应的实体文件
        FileUtils.rm(new File(staticLocationConfig.getFilesPath() + select.getTargetName()));
        // 是否有预览文件 如果有则一并删除
        if (!StringUtils.isBlank(select.getPreviewName())) {
            FileUtils.rm(new File(staticLocationConfig.getFilesPath() + select.getPreviewName()));
        }
        return 1;
    }

    @Override
    public List<SysArticleDataFilesEntity> listByArticleDataId(String sysArticleDataId) {
        return sysArticleDataFilesMapper.listByArticleDataId(sysArticleDataId);
    }

    /**
     * 打包下载文章资料的附件组
     *
     * @param out
     * @param id 文章资料id
     * @return void
     * @author 张朋
     * @date 2019/11/22 14:32
     */
    @Override
    public void downloadPackageFiles(OutputStream out, String id) {
        // 文章资料
        List<SysArticleDataFilesEntity> sysArticleDataFilesEntities = listByArticleDataId(id);

        if (sysArticleDataFilesEntities.size() == 0) {
            throw new ResultException().setResult(Result.success().setReturnMessage("没有附件"));
        }
        // 准备下载
        try (ZipOutputStream zipOut = new ZipOutputStream(out)) {
            for (SysArticleDataFilesEntity fileEntity : sysArticleDataFilesEntities) {
                // 源文件名称
                String sourceName = fileEntity.getSourceName();
                // 物理文件名称 获取文件用
                String targetName = fileEntity.getTargetName();
                String filePath = staticLocationConfig.getFilesPath() + targetName;
                ZipUtils.doZip(sourceName, new File(filePath), zipOut, null);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
