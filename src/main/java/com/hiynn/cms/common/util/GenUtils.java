package com.hiynn.cms.common.util;

import com.hiynn.cms.entity.ColumnEntity;
import com.hiynn.cms.entity.TableEntity;
import com.hiynn.cms.model.vo.DataSourceTableColumnVO;
import com.hiynn.cms.model.vo.DataSourceTableVO;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


/**
 * 代码生成工具
 *
 * @author 张朋
 * @date 2019/11/7 19:21
 */
public class GenUtils {

    private static List<String> getTemplates() {
        List<String> templates = new ArrayList<>();
        templates.add("code-template/Entity.java.vm");
        templates.add("code-template/Mapper.java.vm");
        templates.add("code-template/Mapper.xml.vm");
        templates.add("code-template/Service.java.vm");
        templates.add("code-template/ServiceImpl.java.vm");
        templates.add("code-template/Controller.java.vm");
        return templates;
    }

    /**
     * 生成代码
     */
    public static void generatorCode(
            DataSourceTableVO table,
            List<DataSourceTableColumnVO> columns,
            ZipOutputStream zip,
            String author,
            String packageStr,
            String tablePrefix
    ) {
        //配置信息
        Configuration config = getConfig();
        //表信息
        TableEntity tableEntity = new TableEntity();
        tableEntity.setTableName(table.getTableName());
        tableEntity.setComments(table.getTableComment());
        //表名转换成Java类名
        String className = tableToJava(tableEntity.getTableName(), tablePrefix);
        tableEntity.setClassName(className);
        tableEntity.setClassname(StringUtils.uncapitalize(className));
        // 列信息
        List<ColumnEntity> columsList = new ArrayList<>();
        for (DataSourceTableColumnVO columnVO : columns) {
            ColumnEntity columnEntity = new ColumnEntity();
            columnEntity.setColumnName(columnVO.getColumnName());
            columnEntity.setDataType(columnVO.getDataType());
            columnEntity.setComments(columnVO.getColumnComment());
            columnEntity.setExtra(columnVO.getExtra());
            // 列名转换成Java属性名
            String attrName = columnToJava(columnEntity.getColumnName());
            columnEntity.setAttrName(attrName);
            columnEntity.setAttrname(StringUtils.uncapitalize(attrName));
            // 列的数据类型，转换成Java类型
            String attrType = config.getString(columnEntity.getDataType(), Object.class.getSimpleName());
            columnEntity.setAttrType(attrType);
            // 是否主键
            if (tableEntity.getPk() == null && "PRI".equalsIgnoreCase(columnVO.getColumnKey())) {
                tableEntity.setPk(columnEntity);
            }
            columsList.add(columnEntity);
        }
        tableEntity.setColumns(columsList);
        //没主键，则第一个字段为主键
        if (tableEntity.getPk() == null) {
            tableEntity.setPk(tableEntity.getColumns().get(0));
        }
        //设置velocity资源加载
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);
        // 封装模板数据
        Map<String, Object> map = new HashMap<>(8);
        map.put("tableName", tableEntity.getTableName());
        map.put("comments", tableEntity.getComments());
        map.put("pk", tableEntity.getPk());
        map.put("className", tableEntity.getClassName());
        map.put("classname", tableEntity.getClassname());
        map.put("pathName", tableEntity.getClassname().toLowerCase());
        map.put("columns", tableEntity.getColumns());
        //包名输入
        String packages;
        if (StringUtils.isBlank(packageStr)) {
            packages = config.getString("package");
        } else {
            packages = packageStr;
        }
        map.put("package", packages);
        //使用者输入
        if (StringUtils.isBlank(author)) {
            map.put("author", config.getString("author"));
        } else {
            map.put("author", author);
        }
        // 初始化时间
        map.put("date", DateUtils.getTime(DateUtils.DATE_FORMAT_YYYY_MM_DD__HH_MM_SS));
        // 初始化模板上下文
        VelocityContext context = new VelocityContext(map);
        //获取模板列表
        List<String> templates = getTemplates();
        for (String template : templates) {
            //渲染模板
            Template tpl = Velocity.getTemplate(template, "UTF-8");
            try (StringWriter sw = new StringWriter()) {
                // 模板与上下文合并
                tpl.merge(context, sw);
                //添加到zip
                zip.putNextEntry(
                        new ZipEntry(
                                Objects.requireNonNull(
                                        getFileName(template, tableEntity.getClassName(), packages)
                                )
                        )
                );
                IOUtils.write(sw.toString(), zip, "UTF-8");
                zip.closeEntry();
            } catch (IOException e) {
                throw new RuntimeException("渲染模板失败，表名：" + tableEntity.getTableName(), e);
            }
        }
    }


    /**
     * 列名转换成Java属性名
     */
    private static String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
    }

    /**
     * 表名转换成Java类名
     */
    private static String tableToJava(String tableName, String tablePrefix) {
        if (StringUtils.isNotBlank(tablePrefix)) {
            tableName = tableName.replace(tablePrefix, "");
        }
        return columnToJava(tableName);
    }

    /**
     * 获取配置信息
     */
    private static Configuration getConfig() {
        try {
            return new PropertiesConfiguration("conf/generator.properties");
        } catch (ConfigurationException e) {
            throw new RuntimeException("获取配置文件失败", e);
        }
    }


    /**
     * 获取文件
     */
    private static String getFileName(String template, String className, String packageName) {
        String packagePath = "main" + File.separator + "java" + File.separator;
        if (StringUtils.isNotBlank(packageName)) {
            packagePath += packageName.replace(".", File.separator) + File.separator;
        }

        if (template.contains("Entity.java.vm")) {
            return packagePath + "entity" + File.separator + className + "Entity.java";
        }

        if (template.contains("Mapper.java.vm")) {
            return packagePath + "dao" + File.separator + className + "Mapper.java";
        }

        if (template.contains("Service.java.vm")) {
            return packagePath + "service" + File.separator + className + "Service.java";
        }

        if (template.contains("ServiceImpl.java.vm")) {
            return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
        }

        if (template.contains("Controller.java.vm")) {
            return packagePath + "controller" + File.separator + className + "Controller.java";
        }

        if (template.contains("Mapper.xml.vm")) {
            return packagePath + "dao" + File.separator + "mapper" + File.separator + className + "Mapper.xml";
        }

        return null;
    }
}
