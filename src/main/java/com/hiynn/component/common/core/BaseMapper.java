package com.hiynn.component.common.core;

import java.util.List;


/**
 * 基础Mapper(如使用 请在XML文件中创建对应的ID SQL 节点)
 *
 * @author 张朋
 * @date 2019/10/22 14:16
 */
public interface BaseMapper<T> {


    /**
     * insert
     *
     * @param t entity
     * @return rows
     */
    int insert(T t);

    /**
     * update
     *
     * @param t entity
     * @return rows
     */
    int update(T t);


    /**
     * delete
     *
     * @param id pk
     * @return rows
     */
    int delete(Object id);

    /**
     * query
     *
     * @param id pk
     * @return entity
     */
    T select(Object id);

    /**
     * list page
     *
     * @return List<entity>
     */
    List<T> list();


    /**
     * count all
     *
     * @return rows
     */
    int countTotal();

}
