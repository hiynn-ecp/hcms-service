package com.hiynn.cms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hiynn.cms.common.config.WebStaticLocationConfig;
import com.hiynn.cms.common.util.FileUtils;
import com.hiynn.cms.dao.SysWorkPlatformMapper;
import com.hiynn.cms.entity.SysWorkPlatformEntity;
import com.hiynn.cms.model.vo.PageData;
import com.hiynn.cms.service.SysWorkPlatformService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * 工作平台
 *
 * @author 张朋
 * @date 2019-11-12 18:32:51
 */
@Service
@Slf4j
public class SysWorkPlatformServiceImpl implements SysWorkPlatformService {


    private final SysWorkPlatformMapper sysWorkPlatformMapper;
    private final WebStaticLocationConfig staticLocationConfig;

    @Autowired
    public SysWorkPlatformServiceImpl(SysWorkPlatformMapper sysWorkPlatformMapper,
                                      WebStaticLocationConfig staticLocationConfig) {
        this.sysWorkPlatformMapper = sysWorkPlatformMapper;
        this.staticLocationConfig = staticLocationConfig;
    }


    @Override
    public SysWorkPlatformEntity select(String id) {
        return sysWorkPlatformMapper.select(id);
    }

    @Override
    public PageInfo<SysWorkPlatformEntity> listPage(Integer page, Integer pageSize) {
        log.info("工作平台分页查询：");
        PageHelper.startPage(page, pageSize);
        return new PageData<>(sysWorkPlatformMapper.list());
    }

    @Override
    public int countTotal() {
        return sysWorkPlatformMapper.countTotal();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int insert(SysWorkPlatformEntity sysWorkPlatform, MultipartFile icon) {
        SysWorkPlatformEntity entity = sysWorkPlatform.insert(this.getUserId());
        // 图标
        if (icon != null) {
            entity.setIcon(FileUtils.uploadFile(icon, staticLocationConfig.getImgPath()));
        }
        try {
            return sysWorkPlatformMapper.insert(entity);
        } catch (Exception e) {
            FileUtils.rm(new File(staticLocationConfig.getImgPath() + entity.getIcon()));
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(SysWorkPlatformEntity sysWorkPlatform, MultipartFile icon) {
        // 原数据
        SysWorkPlatformEntity select = this.select(sysWorkPlatform.getId());
        if (select == null) {
            return 0;
        }
        // 初始化
        sysWorkPlatform.update(this.getUserId());
        // 图标
        if (icon != null) {
            String fileName = FileUtils.uploadFile(icon, staticLocationConfig.getImgPath());
            sysWorkPlatform.setIcon(fileName);
        }

        try {
            int cnt = sysWorkPlatformMapper.update(sysWorkPlatform);

            // 如果更新图像 删除旧文件
            if (icon != null) {
                if (cnt > 0) {
                    Boolean rm = FileUtils.rm(new File(staticLocationConfig.getImgPath() + select.getIcon()));
                    log.info("修改成功，删除原图标:{}:{}", rm, staticLocationConfig.getImgPath() + select.getIcon());
                } else {
                    // 如果修改失败 应删除上传的文件
                    FileUtils.rm(new File(staticLocationConfig.getImgPath() + sysWorkPlatform.getIcon()));
                }
            }
            return cnt;
        } catch (Exception e) {
            // 出错删除上传的文件
            FileUtils.rm(new File(staticLocationConfig.getImgPath() + sysWorkPlatform.getIcon()));
            throw new RuntimeException(e);
        }
    }

    @Override
    public int delete(String id) {
        SysWorkPlatformEntity entity = new SysWorkPlatformEntity();
        entity.setId(id);
        entity.setDataStatus(0);
        entity.update(this.getUserId());
        return sysWorkPlatformMapper.update(entity);
    }

}
