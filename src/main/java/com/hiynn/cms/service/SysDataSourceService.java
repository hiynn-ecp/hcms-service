package com.hiynn.cms.service;

import com.hiynn.cms.entity.SysDataSourceEntity;
import com.hiynn.cms.model.dto.DataSourceDTO;
import com.hiynn.cms.model.vo.DataSourceTableColumnVO;
import com.hiynn.cms.model.vo.DataSourceTableVO;
import com.hiynn.cms.model.vo.DataSourceVO;
import com.hiynn.cms.model.vo.PageData;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

/**
 * @author 张朋
 * @date 2019/11/6 15:21
 */
public interface SysDataSourceService extends BaseService {


    /**
     * 删除数据源信息
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(String id);


    /**
     * 插入数据源信息
     *
     * @param record
     * @param files
     * @return
     */
    int insertSelective(SysDataSourceEntity record, MultipartFile[] files);


    /**
     * 查询单个数据源信息
     *
     * @param id
     * @return
     */
    SysDataSourceEntity selectByPrimaryKey(String id);

    /**
     * 修改数据源
     *
     * @param dataSourceDTO
     * @param files
     * @return
     */
    int updateByPrimaryKeySelective(DataSourceDTO dataSourceDTO, MultipartFile[] files);

    /**
     * 查询所有配置的数据源
     *
     * @return
     */
    List<DataSourceVO> listDataSource();


    /**
     * 查询数据表 根据关键字 带分页
     *
     * @return
     */
    PageData<DataSourceTableVO> listTablesByDataSource(String dataSourceId, String tableName, int pageIndex, int pageNum) throws SQLException;

    /**
     * 不带分页
     *
     * @param dataSourceId
     * @param tableName
     * @return
     * @throws SQLException
     */
    List<DataSourceTableVO> listTablesByDataSource(String dataSourceId, String tableName) throws SQLException;

    /**
     * 查询数据源的单个表信息
     *
     * @param dataSourceId
     * @param tableName
     * @return
     */
    DataSourceTableVO selectTable(String dataSourceId, String tableName);

    /**
     * 查询数据源单个表的 字段信息
     *
     * @param dataSourceId
     * @param tableName
     * @return
     */
    List<DataSourceTableColumnVO> listTableColumn(String dataSourceId, String tableName);

    /**
     * 生成代码
     */


    /**
     * 生成代码
     *
     * @param tableNames List
     * @param author String
     * @param packageStr String
     * @param tablePrefix String
     * @return byte[]
     * @author 张朋
     * @date 2019/11/8 10:41
     */
    byte[] generatorCode(String dataSourceId, List<String> tableNames, String author, String packageStr, String tablePrefix);

    /**
     * 刷新数据源
     *
     * @return void
     * @author 张朋
     * @date 2019/11/11 18:03
     */
    void refresh();

    /**
     * 描述: 重置数据源数据
     *
     * @param id
     * @return void
     * @author liuhy
     * @date 2019/12/12 15:15
     */
    void resetDataSource(String id);

}
