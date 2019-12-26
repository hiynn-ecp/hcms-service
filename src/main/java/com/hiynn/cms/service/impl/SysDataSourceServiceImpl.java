package com.hiynn.cms.service.impl;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.hiynn.cms.common.config.WebStaticLocationConfig;
import com.hiynn.cms.common.exception.ResultException;
import com.hiynn.cms.common.factory.DruidDataSourceFactory;
import com.hiynn.cms.common.util.BeanUtils;
import com.hiynn.cms.common.util.FileUtils;
import com.hiynn.cms.common.util.GenUtils;
import com.hiynn.cms.common.util.IDUtils;
import com.hiynn.cms.dao.SysDataSourceEntityMapper;
import com.hiynn.cms.dao.SysDataSourceFilesMapper;
import com.hiynn.cms.entity.SysDataSourceEntity;
import com.hiynn.cms.entity.SysDataSourceFilesEntity;
import com.hiynn.cms.model.dto.DataSourceDTO;
import com.hiynn.cms.model.vo.DataSourceTableColumnVO;
import com.hiynn.cms.model.vo.DataSourceTableVO;
import com.hiynn.cms.model.vo.DataSourceVO;
import com.hiynn.cms.model.vo.PageData;
import com.hiynn.cms.service.SysDataSourceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipOutputStream;

/**
 * @author 张朋
 * @date 2019/11/6 15:21
 */
@Slf4j
@Service
public class SysDataSourceServiceImpl implements SysDataSourceService {

    /**
     * 数据源工厂
     */
    private final DruidDataSourceFactory dataSourceFactory;
    private final SysDataSourceEntityMapper dataSourceMapper;
    private final SysDataSourceFilesMapper dataSourceFilesMapper;
    private final WebStaticLocationConfig staticLocationConfig;
    /**
     * 分页sql
     */
    private final String LIMIT_SQL = " order by TABLE_NAME  limit ?,?";

    /**
     * 查询数据库中所有的表
     */
    private final String LIST_TABLES_SQL = "SELECT TABLE_NAME as tableName,ENGINE as engine,TABLE_COMMENT as tableComment,CREATE_TIME as createTime FROM information_schema.TABLES WHERE TABLE_SCHEMA=? ";

    /**
     * 查询单个表
     */
    private final static String SELECT_TABLE_SQL = "select table_name tableName, engine, table_comment tableComment, create_time createTime from information_schema.tables where table_schema = ? and table_name = ?";

    /**
     * 查询表字段
     */
    private final static String LIST_TABLE_COLUMN = "select column_name columnName, data_type dataType, column_comment columnComment, column_key columnKey, extra from information_schema.columns where table_schema = ? and table_name = ? order by ordinal_position";


    /**
     * 包含关键字的表
     */
    private final String LIST_TABLES_AND_LOCATE_SQL = LIST_TABLES_SQL + " and locate(?,TABLE_NAME) > 0 ";

    /**
     * 总数sql
     */
    private final String LIST_TABLES_COUNT_SQL = "SELECT count(*) FROM information_schema.TABLES WHERE TABLE_SCHEMA=? ";
    private final String LIST_TABLES_COUNT_AND_LOCATE_SQL = LIST_TABLES_COUNT_SQL + " and locate(?,TABLE_NAME) > 0";

    @Autowired
    public SysDataSourceServiceImpl(DruidDataSourceFactory dataSourceFactory,
                                    SysDataSourceEntityMapper dataSourceMapper,
                                    SysDataSourceFilesMapper dataSourceFilesMapper,
                                    WebStaticLocationConfig staticLocationConfig) {
        this.dataSourceFactory = dataSourceFactory;
        this.dataSourceMapper = dataSourceMapper;
        this.dataSourceFilesMapper = dataSourceFilesMapper;
        this.staticLocationConfig = staticLocationConfig;
    }

    /**
     * 删除数据源信息
     *
     * @param id
     * @return
     */
    @Override
    public int deleteByPrimaryKey(String id) {
        SysDataSourceEntity dataSource = new SysDataSourceEntity().update(this.getUserId());
        dataSource.setId(id);
        //  标记删除
        dataSource.setDataStatus(0);
        return dataSourceMapper.updateByPrimaryKeySelective(dataSource);
    }

    /**
     * 插入数据源信息
     *
     * @param record
     * @return
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public int insertSelective(SysDataSourceEntity record, MultipartFile[] files) {
        // 保存数据源
        int i = dataSourceMapper.insertSelective(record.insert(this.getUserId()));
        // 上传
        uploadFiles(files, record.getId());
        return i;
    }

    /**
     * 查询单个数据源信息
     *
     * @param id
     * @return
     */
    @Override
    public SysDataSourceEntity selectByPrimaryKey(String id) {
        return dataSourceMapper.selectByPrimaryKey(id);
    }

    /**
     * 修改数据源
     *
     * @param dataSourceDTO
     * @return
     */
    @Override
    public int updateByPrimaryKeySelective(DataSourceDTO dataSourceDTO, MultipartFile[] files) {
        SysDataSourceEntity record = BeanUtils.copy(dataSourceDTO, SysDataSourceEntity.class);
        record.update(this.getUserId());
        int i = dataSourceMapper.updateByPrimaryKeySelective(record);
        // 上传
        Integer deleteType = dataSourceDTO.getDeleteType();
        // 判断是否附件未更改
        if (deleteType != 0) {
            // 更新附件
            uploadFiles(files, record.getId());
        }
        return i;
    }

    /**
     * 查询所有配置的数据源
     *
     * @return
     */
    @Override
    public List<DataSourceVO> listDataSource() {
        //  查询
        List<DataSourceVO> list = dataSourceMapper.list();

        // 构造视图 添加nameIp属性
        List<DataSourceVO> listVos = list.stream().map(k -> {
            // 截取url中IP
            String url = k.getUrl();
            if (StringUtils.isNotBlank(url)) {
                String substring = "";
                try {
                    substring = url.substring(url.indexOf("//") + 2, url.lastIndexOf("/"));
                } catch (StringIndexOutOfBoundsException e) {
                    log.error("数据源格式不正确", e);
                }
                k.setNameIp(k.getName() + "(" + substring + ")");
            }
            // 给前端判断附件状态的标记 默认为0 未更改状态
            k.setDeleteType(0);
            return k;
        }).collect(Collectors.toList());
        return listVos;
    }

    /**
     * 查询某个数据源的数据表 根据表关键字
     *
     * @param tableName
     * @return
     */
    @Override
    public PageData<DataSourceTableVO> listTablesByDataSource(String dataSourceId, String tableName, int pageIndex, int pageSize) throws SQLException {

        // 获取数据源信息
        SysDataSourceEntity sysDataSourceEntity = dataSourceMapper.selectByPrimaryKey(dataSourceId);
        // 创建数据源
        DruidDataSource dataSource = createDataSource(sysDataSourceEntity);
        JdbcTemplate template = new JdbcTemplate(dataSource);

        // 查询数据表
        //List<Map<String, Object>> maps = null;
        List<DataSourceTableVO> tableVOS = null;
        int total = 0;
        if (StringUtils.isBlank(tableName)) {
            tableVOS = template.query(
                    this.LIST_TABLES_SQL + LIMIT_SQL,
                    new Object[]{sysDataSourceEntity.getName(), (pageIndex - 1) * pageSize, pageSize},
                    new BeanPropertyRowMapper<>(DataSourceTableVO.class)
            );
            total = template.queryForObject(
                    this.LIST_TABLES_COUNT_SQL,
                    new Object[]{sysDataSourceEntity.getName()},
                    Integer.class);
        } else {
            tableVOS = template.query(
                    this.LIST_TABLES_AND_LOCATE_SQL + LIMIT_SQL,
                    new Object[]{sysDataSourceEntity.getName(), tableName, (pageIndex - 1) * pageSize, pageSize},
                    new BeanPropertyRowMapper<>(DataSourceTableVO.class)
            );
            total = template.queryForObject(
                    this.LIST_TABLES_COUNT_AND_LOCATE_SQL,
                    new Object[]{sysDataSourceEntity.getName(), tableName},
                    Integer.class);
        }

        PageData<DataSourceTableVO> pageData = new PageData<>(tableVOS);
        pageData.setPages((total + pageSize - 1) / pageSize);
        pageData.setTotal(total);
        pageData.setPageNum(pageIndex);
        pageData.setPageSize(pageSize);
        return pageData;
    }




    /**
     * 查询某个数据源的数据表 根据表关键字
     *
     * @param tableName
     * @return
     */
    @Override
    public List<DataSourceTableVO> listTablesByDataSource(String dataSourceId, String tableName) throws SQLException {

        // 获取数据源信息
        SysDataSourceEntity sysDataSourceEntity = dataSourceMapper.selectByPrimaryKey(dataSourceId);
        // 创建数据源
        DruidDataSource dataSource = createDataSource(sysDataSourceEntity);
        JdbcTemplate template = new JdbcTemplate(dataSource);

        // 查询数据表
        //List<Map<String, Object>> maps = null;
        List<DataSourceTableVO> tableVOS = null;
        if (StringUtils.isBlank(tableName)) {
            tableVOS = template.query(
                    this.LIST_TABLES_SQL,
                    new Object[]{sysDataSourceEntity.getName()},
                    new BeanPropertyRowMapper<>(DataSourceTableVO.class)
            );
        } else {
            tableVOS = template.query(
                    this.LIST_TABLES_AND_LOCATE_SQL,
                    new Object[]{sysDataSourceEntity.getName(), tableName},
                    new BeanPropertyRowMapper<>(DataSourceTableVO.class)
            );
        }
        return tableVOS;
    }

    /**
     * 查询数据源的单个表信息
     *
     * @param dataSourceId
     * @param tableName
     * @return
     */
    @Override
    public DataSourceTableVO selectTable(String dataSourceId, String tableName) {
        // 获取数据源信息
        SysDataSourceEntity sysDataSourceEntity = dataSourceMapper.selectByPrimaryKey(dataSourceId);
        // 创建数据源
        DruidDataSource dataSource = null;
        try {
            dataSource = createDataSource(sysDataSourceEntity);
        } catch (SQLException e) {
            // 将Exception 包装为runtime 内部消化后全局捕获
            throw new RuntimeException(e);
        }
        // 查询
        JdbcTemplate template = new JdbcTemplate(dataSource);
        List<DataSourceTableVO> tableVOS = template.query(
                SELECT_TABLE_SQL,
                new Object[]{sysDataSourceEntity.getName(), tableName},
                new BeanPropertyRowMapper<>(DataSourceTableVO.class)
        );
        if (tableVOS != null && tableVOS.size() == 1) {
            return tableVOS.get(0);
        }
        return null;
    }

    /**
     * 查询数据源单个表的 字段信息
     *
     * @param dataSourceId
     * @param tableName
     * @return
     */
    @Override
    public List<DataSourceTableColumnVO> listTableColumn(String dataSourceId, String tableName) {
        // 获取数据源信息
        SysDataSourceEntity sysDataSourceEntity = dataSourceMapper.selectByPrimaryKey(dataSourceId);
        // 创建数据源
        DruidDataSource dataSource = null;
        try {
            dataSource = createDataSource(sysDataSourceEntity);
        } catch (SQLException e) {
            // 将Exception 包装为runtime 内部消化后全局捕获
            throw new RuntimeException(e);
        }

        // 查询
        return new JdbcTemplate(dataSource).query(
                LIST_TABLE_COLUMN,
                new Object[]{sysDataSourceEntity.getName(), tableName},
                new BeanPropertyRowMapper<>(DataSourceTableColumnVO.class)
        );
    }

    /**
     * 生成代码
     *
     * @param dataSourceId String
     * @param tableNames List
     * @param author String
     * @param packageStr String
     * @param tablePrefix String
     * @return byte[]
     * @author 张朋
     * @date 2019/11/8 10:41
     */
    @Override
    public byte[] generatorCode(String dataSourceId, List<String> tableNames, String author, String packageStr, String tablePrefix) {

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ZipOutputStream zip = new ZipOutputStream(baos)) {
            for (String tableName : tableNames) {
                // 查询表信息
                DataSourceTableVO table = selectTable(dataSourceId, tableName);
                if (null == table) {
                    throw new RuntimeException("获取数据表失败，请检查传入的数据表名称是否正确");
                }
                // 查询列信息
                List<DataSourceTableColumnVO> columns = listTableColumn(dataSourceId, tableName);
                // 生成代码
                GenUtils.generatorCode(table, columns, zip, author, packageStr, tablePrefix);
            }
            // zip完成刷新到 输出流 但不会关闭输出流 try 会自动关闭
            zip.finish();
            // 开发过程中出现   zip 没有调用 finish 返回的文件不完整
            return baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 刷新数据源
     *
     * @return void
     * @author 张朋
     * @date 2019/11/11 18:03
     */
    @Override
    public void refresh() {
        dataSourceFactory.refreshDruidDataSource();
    }


    @Override
    public void resetDataSource(String id) {
        // 校验数据源是否存在
        SysDataSourceEntity sysDataSourceEntity = dataSourceMapper.selectByPrimaryKey(id);
        if (sysDataSourceEntity == null) {
            throw new ResultException("所选数据源不存在");
        }
        // 校验数据源sql是否存在
        SysDataSourceFilesEntity DataSourceFilesEntity = dataSourceFilesMapper.selectFilesByDataSourceId(id);
        if (DataSourceFilesEntity == null) {
            throw new ResultException("所选数据源重置sql不存在");
        }
        // 重置数据
        // sql地址
        String sqlPath = staticLocationConfig.getSqlPath() + DataSourceFilesEntity.getTargetName();
        log.info("[执行sql文件]sqlPath:" + sqlPath);
        DruidDataSource dataSource = null;
        try {
            dataSource = createDataSource(sysDataSourceEntity);
        } catch (SQLException e) {
            log.error("[执行sql文件]创建dataSource失败", e);
        }
        excuseSql(sqlPath, dataSource);
    }

    /**
     * 创建数据源 单例模式
     *
     * @param sysDataSourceEntity SysDataSourceEntity
     * @return
     * @throws SQLException
     */
    private DruidDataSource createDataSource(SysDataSourceEntity sysDataSourceEntity) throws SQLException {
        // 去工厂获取数据源
        return dataSourceFactory.getDruidDataSource(
                sysDataSourceEntity.getName(),
                sysDataSourceEntity.getUrl(),
                sysDataSourceEntity.getUserName(),
                sysDataSourceEntity.getPassword()
        );

    }

    /**
     * 上传文件
     *
     * @param files
     * @param id
     */
    private void uploadFiles(MultipartFile[] files, String id) {
        // 上传之前先删除数据源之前的sql
        SysDataSourceFilesEntity sysDataSourceFilesEntity = dataSourceFilesMapper.selectFilesByDataSourceId(id);
        if (sysDataSourceFilesEntity != null) {
            // 删除文件
            File file = new File(staticLocationConfig.getSqlPath() + sysDataSourceFilesEntity.getTargetName());
            file.delete();
            // 删除库表记录
            dataSourceFilesMapper.delete(sysDataSourceFilesEntity.getId());
        }
        // 删除附件
        if (files != null && files.length > 0) {
            // 临时上传记录
            List<String> recording = new ArrayList<>();
            // 循环上传
            for (MultipartFile file : files) {
                // 先上传文件并获取文件名
                try {
                    String sourceName = file.getOriginalFilename();
                    String targetName = FileUtils.uploadFile(file, staticLocationConfig.getSqlPath());
                    // 添加临时上传记录
                    recording.add(targetName);
                    // 准备入库数据
                    SysDataSourceFilesEntity filesEntity = new SysDataSourceFilesEntity();
                    filesEntity.setId(IDUtils.getJavaUUID());
                    filesEntity.setCreateTime(new Date());
                    filesEntity.setCreatorId(this.getUserId());
                    filesEntity.setSysDataSourceId(id);
                    filesEntity.setTargetName(targetName);
                    filesEntity.setSourceName(sourceName);

                    // 入库文件信息
                    dataSourceFilesMapper.insert(filesEntity);
                } catch (Exception e) {
                    //清空前面上传的文件
                    for (String fileName : recording) {
                        FileUtils.rm(new File(staticLocationConfig.getSqlPath() + fileName));
                    }
                    // 抛出异常
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * 描述: 执行sql脚本
     * @author liuhy
     * @date 2019/12/13 14:30
     * @param sqlPath
     * @param dataSource
     * @return void
     */
    private void excuseSql(String sqlPath, DruidDataSource dataSource) {
        DruidPooledConnection connection = null;
        ScriptRunner runner = null;
        try {
            connection = dataSource.getConnection();
            runner = new ScriptRunner(connection);
            // 设置不自动提交
            runner.setAutoCommit(false);
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(sqlPath), "utf-8");
            log.info("[执行sql文件]开始执行");
            runner.runScript(inputStreamReader);
            connection.commit();
        } catch (Exception e) {
            try {
                log.info("[执行sql文件]执行出错,开始回滚");
                connection.rollback();
            } catch (SQLException e1) {
                log.info("[执行sql文件]回滚出错");
            }
            log.error("[执行sql文件]执行sql脚本失败", e);
            throw new ResultException("执行sql脚本失败");
        } finally {
            if (runner != null) {
                runner.closeConnection();
            }
        }
    }
}
