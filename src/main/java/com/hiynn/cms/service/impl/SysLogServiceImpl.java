package com.hiynn.cms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hiynn.cms.common.util.IDUtils;
import com.hiynn.cms.dao.SysLogMapper;
import com.hiynn.cms.entity.SysLogEntity;
import com.hiynn.cms.model.vo.PageData;
import com.hiynn.cms.service.SysLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 系统日志记录表
 *
 * @author 张朋
 * @date 2019-11-12 11:17:04
 */
@Service
@Slf4j
public class SysLogServiceImpl implements SysLogService {


    private final SysLogMapper sysLogMapper;

    @Autowired
    public SysLogServiceImpl(SysLogMapper sysLogMapper) {
        this.sysLogMapper = sysLogMapper;
    }


    @Override
    public SysLogEntity select(String id) {
        return sysLogMapper.select(id);
    }

    @Override
    public PageInfo<?> listPage(String page, String pageSize) {
        log.info("SysLog-分页查询：");
        PageHelper.startPage(Integer.valueOf(page), Integer.valueOf(pageSize));
        return new PageData<>(sysLogMapper.list());
    }


    @Override
    public PageInfo<SysLogEntity> selectForList(int pageIndex, int pageSize, String searchKey, Date startTime, Date endTime) {
        log.info("SysLog-多条件分页查询：");
        PageHelper.startPage(pageIndex, pageSize);
        return new PageData<>(sysLogMapper.selectForList(searchKey, startTime, endTime));
    }

    @Override
    public int insert(SysLogEntity sysLog) {
        sysLog.setId(IDUtils.getJavaUUID());
        sysLog.setCreatorId(this.getUserId());
        sysLog.setCreateTime(new Date());
        return sysLogMapper.insert(sysLog);
    }

}
