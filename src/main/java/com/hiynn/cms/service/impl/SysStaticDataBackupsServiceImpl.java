package com.hiynn.cms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hiynn.cms.common.config.WebStaticLocationConfig;
import com.hiynn.cms.common.exception.ResultException;
import com.hiynn.cms.common.util.FileUtils;
import com.hiynn.cms.common.util.SendCmdUtil;
import com.hiynn.cms.dao.SysStaticDataBackupsMapper;
import com.hiynn.cms.dao.SysStaticDataMapper;
import com.hiynn.cms.entity.SysStaticDataBackupsEntity;
import com.hiynn.cms.entity.SysStaticDataEntity;
import com.hiynn.cms.model.vo.BackupsVO;
import com.hiynn.cms.service.SysStaticDataBackupsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 描述: 静态资源备份表
 * @author liuhy
 * @date 2019-12-24 10:54:07
 */
@Service
@Slf4j
public class SysStaticDataBackupsServiceImpl implements SysStaticDataBackupsService {


    private final SysStaticDataBackupsMapper sysStaticDataBackupsMapper;

    private final SysStaticDataMapper sysStaticDataMapper;

    private final WebStaticLocationConfig staticLocationConfig;

    @Autowired
    public SysStaticDataBackupsServiceImpl(SysStaticDataBackupsMapper sysStaticDataBackupsMapper,
                                           WebStaticLocationConfig staticLocationConfig,
                                           SysStaticDataMapper sysStaticDataMapper) {
        this.sysStaticDataBackupsMapper = sysStaticDataBackupsMapper;
        this.staticLocationConfig = staticLocationConfig;
        this.sysStaticDataMapper = sysStaticDataMapper;
    }

    /**
     * 描述: ID查询
     * @author liuhy
     * @date 2019-12-24 10:54:07
     * @param id
     * @return SysStaticDataBackupsEntity
     */
    @Override
    public SysStaticDataBackupsEntity select(String id) {
        return sysStaticDataBackupsMapper.select(id);
    }

    /**
     * 描述: 分页查询
     * @author liuhy
     * @date 2019-12-24 10:54:07
     * @param page
     * @param pageSize
     * @return PageInfo
     */
    @Override
    public PageInfo<?> listPage(String page, String pageSize,String sysStaticDataId) {
        log.info("{}{}{}", "分页查询：",page,pageSize);
        PageHelper.startPage(Integer.valueOf(page), Integer.valueOf(pageSize));
        List<BackupsVO> selectByPage = sysStaticDataBackupsMapper.listPage(sysStaticDataId);
        PageInfo<BackupsVO> pageInfo = new PageInfo<>(selectByPage);
        log.info("{}{}", "分页查询返回==>", pageInfo);
        return pageInfo;
    }

    /**
     * 描述: 查询总数
     * @author liuhy
     * @date 2019-12-24 10:54:07
     * @param
     * @return int
     */
    @Override
    public int countTotal() {
        return sysStaticDataBackupsMapper.countTotal();
    }

    /**
     * 描述: 保存
     * @author liuhy
     * @date 2019-12-24 10:54:07
     * @param staticDataId
     * @return int
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insert(String staticDataId) {
        // 查询静态数据源
        SysStaticDataEntity staticDataEntity = sysStaticDataMapper.select(staticDataId);
        if (staticDataEntity == null) {
            throw new ResultException("所选静态资源不存在,无法备份");
        }

        // 备份资源路径创建
        File file = new File(staticLocationConfig.getBackupsPath() + staticDataId);
        log.info("file创建:{}",file);
        if(!file.exists()){
            file.mkdirs();
        }
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        // 备份资源存放路径
        String targetFilePath=staticLocationConfig.getBackupsPath()+ staticDataId;
        // 本分资源名称
        String name="backups-"+now+".tar.gz";
        String cmd="tar -zcvf "+targetFilePath+File.separator+name+" "+staticDataEntity.getPath();
        Map<String, Object> map = null;
        try {
            SendCmdUtil.sendCommand(cmd);
            // 检查执行结果
            String checkCmd="ls "+targetFilePath;
            map = SendCmdUtil.sendCommand(checkCmd);
        } catch (Exception e) {
            log.error("cmd执行失败",e);
        }
        // 检查结果是否正确
        if(map!=null){
            Object success = map.get("success");
            if (success!=null && success instanceof List){
                List list=(List)success;
                if (list.size()>0){
                    // 正确数据库添加一条
                    SysStaticDataBackupsEntity backupsEntity = new SysStaticDataBackupsEntity();
                    backupsEntity.setName(name);
                    backupsEntity.setPath(targetFilePath);
                    backupsEntity.setSysStaticDataId(staticDataId);
                    sysStaticDataBackupsMapper.insert(backupsEntity.insert(this.getUserId()));
                }
            }
        }
        return 1;
    }

    /**
     * 描述: ID删除
     * @author liuhy
     * @date 2019-12-24 10:54:07
     * @param id
     * @return int
     */
    @Override
    public int delete(String id) {
        // 删除文件
        SysStaticDataBackupsEntity backupsEntity = sysStaticDataBackupsMapper.select(id);
        FileUtils.rm(new File(backupsEntity.getPath() + File.separator + backupsEntity.getName()));
        return sysStaticDataBackupsMapper.delete(id);
    }

    /**
     * 描述: 恢复静态文件
     * @author liuhy
     * @date 2019/12/24 14:45
     * @param id
     * @return void
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void recover(String id) {
        // 查询记录
        SysStaticDataBackupsEntity backupsEntity = sysStaticDataBackupsMapper.select(id);
        if(backupsEntity==null){
            throw new ResultException("所选备份不存在");
        }

        // 执行恢复命令
        String cmd="tar -zxvf "+backupsEntity.getPath()+File.separator+backupsEntity.getName()+" -C /";
        try {
            SendCmdUtil.sendCommand(cmd);
        } catch (Exception e) {
            throw new RuntimeException("获取重置备份列表,请重试");
        }
        // 更新恢复记录
        backupsEntity.setRecoverId(this.getUserId());
        backupsEntity.setRecoverTime(new Date());
        sysStaticDataBackupsMapper.update(backupsEntity);
    }

    @Override
    public void update(String id, String remark) {
        SysStaticDataBackupsEntity backupsEntity = new SysStaticDataBackupsEntity();
        backupsEntity.setId(id);
        backupsEntity.setRemark(remark);
        backupsEntity.update(this.getUserId());
        sysStaticDataBackupsMapper.update(backupsEntity);
    }

}
