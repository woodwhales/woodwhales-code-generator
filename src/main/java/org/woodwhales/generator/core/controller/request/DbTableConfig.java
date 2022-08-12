package org.woodwhales.generator.core.controller.request;

import lombok.Data;

import java.util.List;

/**
 * @author woodwhales on 22.8.12 13:49
 */
@Data
public class DbTableConfig {

    /**
     * 要生成的数据库表名列表
     */
    private List<String> dbNameList;

    /**
     * 是否生成全部数据库表
     */
    private Boolean selectAll;

}
