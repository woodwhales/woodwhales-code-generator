package org.woodwhales.generator.core.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author woodwhales on 2020-09-11
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColsConfigVO {

    /**
     * 数据库字段名
     */
    private String dbColsField;

    /**
     * 变量
     */
    private String colsField;

    /**
     * 标题
     */
    private String colsTitle;

    /**
     * 宽度
     */
    private Integer colsWidth;

    /**
     * 表字段类型
     */
    private String dbType;

    /**
     * 字段大小
     */
    private int columnSize;

    /**
     * 排序
     */
    private Integer sort;

}
