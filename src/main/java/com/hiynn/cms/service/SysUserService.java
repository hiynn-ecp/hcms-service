package com.hiynn.cms.service;

import com.github.pagehelper.PageInfo;
import com.hiynn.cms.entity.SysUserEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 用户表
 *
 * @author 张朋
 * @date 2019-10-24 17:19:52
 */
public interface SysUserService extends BaseService {


    /**
     * 更具账户名称获取用户信息
     *
     * @param account 账户
     * @return SysUserEntity
     */
    SysUserEntity selectByAccount(String account);


    /**
     * ID查询
     */
    SysUserEntity select(String id);

    /**
     * 分页查询
     */
    PageInfo<SysUserEntity> listPage(Integer page, Integer pageSize);

    /**
     * 查询总数
     */
    int countTotal();

    /**
     * 保存
     */
    int insert(SysUserEntity user, MultipartFile picture) throws IOException;

    /**
     * 更新 禁止修改用户密码 与 账户
     */
    int update(SysUserEntity user, MultipartFile picture) throws IOException;

    /**
     * ID删除
     */
    int delete(String id);

}
