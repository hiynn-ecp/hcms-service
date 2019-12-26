package com.hiynn.cms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hiynn.cms.common.config.WebStaticLocationConfig;
import com.hiynn.cms.common.converter.DocConverter;
import com.hiynn.cms.common.util.BeanUtils;
import com.hiynn.cms.common.util.FileUtils;
import com.hiynn.cms.common.util.IDUtils;
import com.hiynn.cms.common.util.ValidatorUtils;
import com.hiynn.cms.dao.SysArticleDataFilesMapper;
import com.hiynn.cms.dao.SysArticleDataMapper;
import com.hiynn.cms.entity.SysArticleDataEntity;
import com.hiynn.cms.entity.SysArticleDataFilesEntity;
import com.hiynn.cms.model.vo.ArticleDataVO;
import com.hiynn.cms.model.vo.PageData;
import com.hiynn.cms.service.SysArticleDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 文章资料 业务类
 *
 * @author 张朋
 * @date 2019-11-12 18:32:50
 */
@Service
@Slf4j
public class SysArticleDataServiceImpl implements SysArticleDataService {

    private final SysArticleDataMapper sysArticleDataMapper;
    private final WebStaticLocationConfig staticLocationConfig;
    private final SysArticleDataFilesMapper sysArticleDataFilesMapper;
    private final DocConverter docConverter;

    @Autowired
    public SysArticleDataServiceImpl(SysArticleDataMapper sysArticleDataMapper,
                                     WebStaticLocationConfig staticLocationConfig,
                                     SysArticleDataFilesMapper sysArticleDataFilesMapper,
                                     DocConverter docConverter) {
        this.sysArticleDataMapper = sysArticleDataMapper;
        this.staticLocationConfig = staticLocationConfig;
        this.sysArticleDataFilesMapper = sysArticleDataFilesMapper;
        this.docConverter = docConverter;
    }

    @Override
    public SysArticleDataEntity select(String id) {
        return BeanUtils.copy(sysArticleDataMapper.select(id), ArticleDataVO.class);
    }

    /**
     * 分页查询 根据类型 与 标题
     *
     * @param page
     * @param pageSize
     * @param type
     * @param title
     * @return com.github.pagehelper.PageInfo<com.hiynn.cms.entity.SysArticleDataEntity>
     * @author 张朋
     * @date 2019/11/14 18:48
     */
    @Override
    public PageInfo<SysArticleDataEntity> listByTypeOrTitle(Integer page, Integer pageSize, Integer type, String title) {
        log.info("‘有’条件分页查询文章资料");
        PageHelper.startPage(page, pageSize);
        List<SysArticleDataEntity> selectByPage = sysArticleDataMapper.listByTypeOrTitle(type, title);
        return new PageData<>(selectByPage);
    }


    @Override
    public PageInfo<SysArticleDataEntity> listPage(Integer page, Integer pageSize) {
        log.info("‘无’条件分页查询文章资料");
        PageHelper.startPage(page, pageSize);
        List<SysArticleDataEntity> selectByPage = sysArticleDataMapper.list();
        return new PageData<>(selectByPage);
    }

    @Override
    public int countTotal() {
        return sysArticleDataMapper.countTotal();
    }


    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int insert(SysArticleDataEntity sysArticleData, MultipartFile[] files) {
        // 插入初始化
        sysArticleData.insert(this.getUserId());
        // 开始插入文章资料
        sysArticleDataMapper.insert(sysArticleData);
        // 是否有附件
        if (files != null && files.length > 0) {
            // 临时上传记录
            List<String> recording = new ArrayList<>();
            // 循环上传
            for (MultipartFile file : files) {
                // 先上传文件并获取文件名
                try {
                    String sourceName = file.getOriginalFilename();
                    String targetName = FileUtils.uploadFile(file, staticLocationConfig.getFilesPath());
                    // 添加临时上传记录
                    recording.add(targetName);
                    // 准备入库数据
                    SysArticleDataFilesEntity filesEntity = new SysArticleDataFilesEntity();
                    filesEntity.setId(IDUtils.getJavaUUID());
                    filesEntity.setCreateTime(new Date());
                    filesEntity.setCreatorId(this.getUserId());
                    filesEntity.setSysArticleDataId(sysArticleData.getId());
                    filesEntity.setTargetName(targetName);
                    filesEntity.setSourceName(sourceName);

                    // 判断是否为doc类文件 如果是 则生成预览文件
                    boolean doc = ValidatorUtils.isDoc(targetName);
                    // doc类
                    if (doc) {
                        // 转换
                        String pdfName = docConverter.docToPdf(staticLocationConfig.getFilesPath() + targetName, staticLocationConfig.getFilesPath() + targetName);
                        // 添加预览文件生成记录
                        recording.add(pdfName);
                        // 预览文件入库
                        filesEntity.setPreviewName(pdfName);
                    }
                    // 入库文件信息
                    sysArticleDataFilesMapper.insert(filesEntity);
                } catch (Exception e) {
                    //清空前面上传的文件
                    for (String fileName : recording) {
                        FileUtils.rm(
                                new File(staticLocationConfig.getFilesPath() + fileName)
                        );
                    }
                    // 抛出异常
                    throw new RuntimeException(e);
                }
            }
        }
        return 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(SysArticleDataEntity sysArticleData, MultipartFile[] files) {
        // 修改文章资料数据
        int update = sysArticleDataMapper.update(sysArticleData.update(this.getUserId()));
        if (update == 0) {
            return 0;
        }
        // 是否有新上传的文件
        if (files != null && files.length > 0) {
            // 临时上传记录
            List<String> recording = new ArrayList<>();
            // 循环上传
            for (MultipartFile file : files) {
                // 先上传文件并获取文件名
                try {
                    String sourceName = file.getOriginalFilename();
                    String targetName = FileUtils.uploadFile(file, staticLocationConfig.getFilesPath());
                    // 添加临时上传记录
                    recording.add(targetName);
                    // 准备入库数据
                    SysArticleDataFilesEntity filesEntity = new SysArticleDataFilesEntity();
                    filesEntity.setId(IDUtils.getJavaUUID());
                    filesEntity.setCreateTime(new Date());
                    filesEntity.setCreatorId(this.getUserId());
                    filesEntity.setSysArticleDataId(sysArticleData.getId());
                    filesEntity.setTargetName(targetName);
                    filesEntity.setSourceName(sourceName);
                    // 判断是否为doc类文件 如果是 则生成预览文件
                    boolean doc = ValidatorUtils.isDoc(targetName);
                    // doc类
                    if (doc) {
                        // 转换
                        String pdfName = docConverter.docToPdf(staticLocationConfig.getFilesPath() + targetName, staticLocationConfig.getFilesPath() + targetName);
                        // 添加预览文件生成记录
                        recording.add(pdfName);
                        // 预览文件入库
                        filesEntity.setPreviewName(pdfName);
                    }
                    // 入库文件信息
                    sysArticleDataFilesMapper.insert(filesEntity);
                } catch (Exception e) {
                    //清空前面上传的文件
                    for (String fileName : recording) {
                        FileUtils.rm(new File(staticLocationConfig.getFilesPath() + fileName));
                    }
                    // 清空完毕 抛出异常 执行回滚
                    throw new RuntimeException(e);
                }
            }
        }
        return 1;
    }

    @Override
    public int delete(String id) {
        // 初始化删除模型
        SysArticleDataEntity articleDataEntity = new SysArticleDataEntity().update(this.getUserId());
        articleDataEntity.setId(id);
        articleDataEntity.setDataStatus(0);
        // 执行软删除 关联表数据保留
        return sysArticleDataMapper.update(articleDataEntity);
    }



}
