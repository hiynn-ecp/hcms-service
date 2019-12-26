package com.hiynn.cms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hiynn.cms.common.config.WebStaticLocationConfig;
import com.hiynn.cms.common.shiro.ShiroEncryption;
import com.hiynn.cms.common.util.BeanUtils;
import com.hiynn.cms.common.util.FileUtils;
import com.hiynn.cms.dao.SysUserMapper;
import com.hiynn.cms.entity.SysUserEntity;
import com.hiynn.cms.model.vo.PageData;
import com.hiynn.cms.model.vo.UserVO;
import com.hiynn.cms.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户表
 *
 * @author 张朋
 * @date 2019-10-24 17:19:52
 */
@Service
@Slf4j
public class SysUserServiceImpl implements SysUserService {

    private final SysUserMapper sysUserMapper;
    private final WebStaticLocationConfig staticLocationConfig;

    @Autowired
    public SysUserServiceImpl(SysUserMapper sysUserMapper, WebStaticLocationConfig staticLocationConfig) {
        this.sysUserMapper = sysUserMapper;
        this.staticLocationConfig = staticLocationConfig;
    }

    /**
     * 根据账户名称获取用户信息
     *
     * @param account 账户
     * @return SysUserEntity
     */
    @Override
    public SysUserEntity selectByAccount(String account) {
        return sysUserMapper.selectByAccount(account);
    }

    @Override
    public SysUserEntity select(String id) {
        return sysUserMapper.select(id);
    }


    @Override
    public PageInfo<SysUserEntity> listPage(Integer page, Integer pageSize) {
        // 分页
        PageHelper.startPage(page, pageSize);
        // 查询
        List<SysUserEntity> listPage = sysUserMapper.list();

        // 重写pageinfo
        PageInfo<SysUserEntity> pageInfo = new PageData<>(listPage);
        // 属性转换
        List<SysUserEntity> users = new ArrayList<>();
        for (SysUserEntity user : pageInfo.getList()) {
            users.add(BeanUtils.copy(user, UserVO.class));
        }
        pageInfo.setList(users);
        return pageInfo;
    }

    @Override
    public int countTotal() {
        return sysUserMapper.countTotal();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int insert(SysUserEntity sysUser, MultipartFile picture) {
        sysUser.insert(this.getUserId());
        // 有头像
        if (picture != null) {
            // 上传文件并返回新的唯一名称
            sysUser.setPicture(FileUtils.uploadFile(picture, staticLocationConfig.getImgPath()));
        }
        try {
            // 生成盐
            String salt = ShiroEncryption.getSalt();
            // 加密密码 md5 2次hash
            String encodePassword = ShiroEncryption.shiroEncryption(sysUser.getPassword(), salt);
            sysUser.setSalt(salt);
            sysUser.setPassword(encodePassword);
            return sysUserMapper.insert(sysUser);
        } catch (Exception e) {
            // 出现异常的情况下 进行删除上传的图片
            FileUtils.rm(new File(staticLocationConfig.getImgPath() + sysUser.getPicture()));
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(SysUserEntity sysUser, MultipartFile picture) {
        // 原数据
        SysUserEntity select = sysUserMapper.select(sysUser.getId());
        if (null == select) {
            return 0;
        }
        // 初始化
        sysUser.update(this.getUserId());
        // 修改头像
        if (picture != null) {
            // 上传新头像并返回文件名称
            sysUser.setPicture(FileUtils.uploadFile(picture, staticLocationConfig.getImgPath()));
        }
        try {
            // 如果修改密码
            if (sysUser.getPassword() != null) {
                // 生成盐
                String salt = ShiroEncryption.getSalt();
                // 加密密码 md5 2次hash
                String encodePassword = ShiroEncryption.shiroEncryption(sysUser.getPassword(), salt);
                sysUser.setSalt(salt);
                sysUser.setPassword(encodePassword);
            }
            int cnt = sysUserMapper.update(sysUser);

            if (picture != null) {
                if (cnt > 0) {
                    // 删除原头像
                    Boolean rmResult = FileUtils.rm(new File(staticLocationConfig.getImgPath() + select.getPicture()));
                    log.info("修改成功，删除原头像:{}:{}", rmResult, staticLocationConfig.getImgPath() + select.getPicture());
                } else {
                    // 修改失败删除上传的头像
                    Boolean rmResult = FileUtils.rm(new File(staticLocationConfig.getImgPath() + sysUser.getPicture()));
                    log.info("修改失败，删除上传头像:{}:{}", rmResult, staticLocationConfig.getImgPath() + sysUser.getPicture());
                }
            }
            return cnt;
        } catch (Exception e) {
            // 出现异常删除上传的头像
            FileUtils.rm(new File(staticLocationConfig.getImgPath() + sysUser.getPicture()));
            throw new RuntimeException(e);
        }
    }

    @Override
    public int delete(String id) {
        // 当前操作用户
        SysUserEntity delUser = new SysUserEntity().update(this.getUserId());
        delUser.setId(id);
        // 标记删除
        delUser.setDataStatus(0);
        return sysUserMapper.update(delUser);
    }

}
