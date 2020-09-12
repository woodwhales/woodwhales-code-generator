package org.woodwhales.generator.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author woodwhales on 2020-09-11
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NavigationConfigVO {

    /**
     * 数据库表名
     */
    private String tableName;

    /**
     * cite名称
     */
    private String citeName;

    /**
     * tab名称
     */
    private String addTabTitle;

    /**
     * tab链接
     */
    private String addTabUrl;

    /**
     * tab排序
     */
    private Integer sort;

}
