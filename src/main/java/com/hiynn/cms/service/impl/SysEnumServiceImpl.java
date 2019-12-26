package com.hiynn.cms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hiynn.cms.dao.SysEnumMapper;
import com.hiynn.cms.entity.SysEnumEntity;
import com.hiynn.cms.model.vo.PageData;
import com.hiynn.cms.service.SysEnumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统枚举表
 *
 * @author 张朋
 * @date 2019-11-11 10:48:04
 */
@Service
@Slf4j
public class SysEnumServiceImpl implements SysEnumService {


    private final SysEnumMapper sysEnumMapper;

    @Autowired
    public SysEnumServiceImpl(SysEnumMapper sysEnumMapper) {
        this.sysEnumMapper = sysEnumMapper;
    }


    @Override
    public SysEnumEntity select(String id) {
        return sysEnumMapper.select(id);
    }

    @Override
    public PageInfo<?> listPage(Integer page, Integer pageSize, String type) {
        log.info("枚举listPageOrByType查询：");
        PageHelper.startPage(page,pageSize);
        return new PageData<>(sysEnumMapper.listPageOrByType(type));
    }

    @Override
    public int countTotal() {
        return sysEnumMapper.countTotal();
    }


    @Override
    public int insert(SysEnumEntity sysEnum) {
        return sysEnumMapper.insert(sysEnum.insert(this.getUserId()));
    }

    @Override
    public int update(SysEnumEntity sysEnum) {
        return sysEnumMapper.update(sysEnum.update(this.getUserId()));
    }

    @Override
    public int delete(String id) {
        SysEnumEntity sysEnum = new SysEnumEntity();
        // 补全必要参数
        sysEnum.setId(id);
        // 软删除
        sysEnum.setDataStatus(0);
        return sysEnumMapper.update(sysEnum.update(this.getUserId()));
    }

    /**
     * 根据类型查询一组枚举
     *
     * @param type
     * @return
     */
    @Override
    public List<SysEnumEntity> listByType(String type) {
        return sysEnumMapper.listByType(type);
    }


}
