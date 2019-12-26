package com.hiynn.cms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hiynn.cms.dao.SysPlatformProductMapper;
import com.hiynn.cms.entity.SysPlatformProductEntity;
import com.hiynn.cms.model.vo.PageData;
import com.hiynn.cms.service.SysPlatformProductService;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 平台产品
 *
 * @author 张朋
 * @date 2019-11-12 18:32:50
 */
@Service
@Slf4j
public class SysPlatformProductServiceImpl implements SysPlatformProductService {


    private final SysPlatformProductMapper sysPlatformProductMapper;

    @Autowired
    public SysPlatformProductServiceImpl(SysPlatformProductMapper sysPlatformProductMapper) {
        this.sysPlatformProductMapper = sysPlatformProductMapper;
    }


    @Override
    public SysPlatformProductEntity select(String id) {
        return sysPlatformProductMapper.select(id);
    }

    @Override
    public PageInfo<SysPlatformProductEntity> listPage(Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<SysPlatformProductEntity> selectByPage = sysPlatformProductMapper.list();
        return new PageData<>(selectByPage);
    }

    @Override
    public int countTotal() {
        return sysPlatformProductMapper.countTotal();
    }

    @Override
    public int insert(SysPlatformProductEntity sysPlatformProduct) {
        return sysPlatformProductMapper.insert(sysPlatformProduct.insert(this.getUserId()));
    }

    @Override
    public int update(SysPlatformProductEntity sysPlatformProduct) {
        return sysPlatformProductMapper.update(sysPlatformProduct.update(this.getUserId()));
    }

    @Override
    public int delete(String id) {
        SysPlatformProductEntity platformProduct = new SysPlatformProductEntity().update(this.getUserId());
        platformProduct.setId(id);
        platformProduct.setDataStatus(0);
        return sysPlatformProductMapper.update(platformProduct);
    }


}
