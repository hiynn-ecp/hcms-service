package com.hiynn.cms.entity;

import java.util.List;

/**
 * 模板渲染使用的数据模型
 * <p>
 * 本实体 不可以使用lombok注解 因为 className 名称有歧义
 *
 * @author 张朋
 * @date 2019/11/7 18:51
 */
public class TableEntity {
    /**
     * 表的名称
     */

    private String tableName;

    /**
     * 表的备注
     */
    private String comments;

    /**
     * 表的主键
     */
    private ColumnEntity pk;

    /**
     * 表的列名
     */
    private List<ColumnEntity> columns;
    /**
     * 开头字母大写类名
     */
    private String className;

    /**
     * 开头小写类名
     */
    private String classname;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public ColumnEntity getPk() {
        return pk;
    }

    public void setPk(ColumnEntity pk) {
        this.pk = pk;
    }

    public List<ColumnEntity> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnEntity> columns) {
        this.columns = columns;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }
}
