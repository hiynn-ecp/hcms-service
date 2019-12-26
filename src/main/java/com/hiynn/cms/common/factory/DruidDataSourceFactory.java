package com.hiynn.cms.common.factory;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Druid数据源工厂
 *
 * @author 张朋
 * @date 2019/11/6 15:03
 */
@Component
@Slf4j
public class DruidDataSourceFactory {

    /**
     * 存储数据源
     */
    private final ConcurrentHashMap<String, DruidDataSource> druidDataSourceConcurrentHashMap = new ConcurrentHashMap<>(16);

    /**
     * 可重入锁
     */
    private ReentrantLock druidDataSourceConcurrentHashMapLock = new ReentrantLock(true);

    /**
     * 构建DruidDataSource
     *
     * @param url
     * @param userName
     * @param passWord
     * @return DruidDataSource
     */
    private DruidDataSource builder(String dataSourceName, String url, String userName, String passWord) throws SQLException {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(url);
        druidDataSource.setPassword(passWord);
        druidDataSource.setUsername(userName);
        return druidDataSource;
    }

    /**
     * 获取数据源
     *
     * @param dataSourceName
     * @param url
     * @param userName
     * @param passWord
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public DruidDataSource getDruidDataSource(String dataSourceName, String url, String userName, String passWord) throws SQLException {
        DruidDataSource druidDataSource;
        try {
            // 开始锁
            this.druidDataSourceConcurrentHashMapLock.lock();
            // 如果存在
            if (druidDataSourceConcurrentHashMap.containsKey(dataSourceName)) {
                druidDataSource = druidDataSourceConcurrentHashMap.get(dataSourceName);
            } else {
                // 不存在
                druidDataSource = builder(dataSourceName, url, userName, passWord);
                // 增加到本地缓存
                druidDataSourceConcurrentHashMap.put(dataSourceName, druidDataSource);
            }
            log.info("--动态数据源--{}" +
                            "HASH_CODE:{}{}" +
                            "数据源详情:{}",
                    System.lineSeparator(),
                    druidDataSource.hashCode(),
                    System.lineSeparator(),
                    druidDataSource.toString());
        } finally {
            // 释放锁
            this.druidDataSourceConcurrentHashMapLock.unlock();
        }
        return druidDataSource;
    }

    /**
     * 删除数据源
     *
     * @param dataSourceName
     */
    public void removeDruidDataSource(String dataSourceName) {
        try {
            this.druidDataSourceConcurrentHashMapLock.lock();
            if (druidDataSourceConcurrentHashMap.containsKey(dataSourceName)) {
                //先关闭数据源返回连接 以免造成资源浪费
                druidDataSourceConcurrentHashMap.get(dataSourceName).close();
                druidDataSourceConcurrentHashMap.remove(dataSourceName);
            }
        } finally {
            this.druidDataSourceConcurrentHashMapLock.unlock();
        }
    }


    /**
     * 刷新数据源
     */
    public void refreshDruidDataSource() {
        try {
            this.druidDataSourceConcurrentHashMapLock.lock();
            // 清空缓存
            druidDataSourceConcurrentHashMap.clear();
        } finally {
            this.druidDataSourceConcurrentHashMapLock.unlock();
        }
    }


}
