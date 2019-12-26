package com.hiynn.cms.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 数据源表格列的视图模型
 * <p>
 * 此模型属于内部使用没有参与视图渲染
 *
 * @author 张朋
 * @date 2019/11/7 16:31
 */
@Data
public class DataSourceTableColumnVO implements Serializable {
    private static final long serialVersionUID = -8459017041094117474L;
    /**
     * 列名称
     */
    private String columnName;
    /**
     * 数据类型
     */
    private String dataType;
    /**
     * 列备注
     */
    private String columnComment;
    /**
     * 索引
     */
    private String columnKey;
    /**
     * 扩展信息
     */
    private String extra;

}
