package com.hiynn.cms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hiynn.cms.dao.SysStaticDataMapper;
import com.hiynn.cms.entity.SysStaticDataEntity;
import com.hiynn.cms.model.vo.StaticDataVO;
import com.hiynn.cms.service.SysStaticDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 描述: 静态资源表(只保存静态资源路径)
 * @author liuhy
 * @date 2019-12-24 10:54:07
 */
@Service
@Slf4j
public class SysStaticDataServiceImpl implements SysStaticDataService {


    private final SysStaticDataMapper sysStaticDataMapper;

    @Autowired
    public SysStaticDataServiceImpl(SysStaticDataMapper sysStaticDataMapper) {
        this.sysStaticDataMapper = sysStaticDataMapper;
    }

    /**
     * 描述: ID查询
     * @author liuhy
     * @date 2019-12-24 10:54:07
     * @param id
     * @return SysStaticDataEntity
     */
    @Override
    public SysStaticDataEntity select(String id) {
        return sysStaticDataMapper.select(id);
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
    public PageInfo<?> listPage(String page, String pageSize) {
        log.info("{}{}{}", "分页查询：",page,pageSize);
        PageHelper.startPage(Integer.valueOf(page), Integer.valueOf(pageSize));
        List<StaticDataVO> selectByPage = sysStaticDataMapper.listPage();
        PageInfo<StaticDataVO> pageInfo = new PageInfo<>(selectByPage);
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
        return sysStaticDataMapper.countTotal();
    }

    /**
     * 描述: 保存
     * @author liuhy
     * @date 2019-12-24 10:54:07
     * @param sysStaticData
     * @return int
     */
    @Override
    public int insert(SysStaticDataEntity sysStaticData) {
        return sysStaticDataMapper.insert(sysStaticData.insert(this.getUserId()));
    }

    /**
     * 描述: 更新
     * @author liuhy
     * @date 2019-12-24 10:54:07
     * @param sysStaticData
     * @return int
     */
    @Override
    public int update(SysStaticDataEntity sysStaticData) {
        return sysStaticDataMapper.update(sysStaticData.update(this.getUserId()));
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
        SysStaticDataEntity staticDataEntity = new SysStaticDataEntity();
        staticDataEntity.setId(id);
        staticDataEntity.setDataStatus(0);
        return sysStaticDataMapper.update(staticDataEntity);
    }
	
}
