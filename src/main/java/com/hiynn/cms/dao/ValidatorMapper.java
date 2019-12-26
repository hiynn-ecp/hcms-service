package com.hiynn.cms.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 数据有效性校验
 *
 * @author 张朋
 * @date 2019/11/19 15:00
 */
@Mapper
public interface ValidatorMapper {

    /**
     * 检查枚举表 dataKey 是否存在
     *
     * @param key
     * @param type
     * @return int
     * @author 张朋
     * @date 2019/11/19 15:15
     */
    int isEnums(@Param("key") Integer key, @Param("type") String type);

    /**
     * 检查用户是否存在
     *
     * @param id 用户id
     * @return int
     */
    int isUser(String id);

    /**
     * 检查角色是否存在
     *
     * @param id 角色id
     * @return int
     */
    int isRole(String id);

    /**
     * 检查产品是否存在
     *
     * @param id menuId
     * @return int
     */
    int isMenu(String id);

}
