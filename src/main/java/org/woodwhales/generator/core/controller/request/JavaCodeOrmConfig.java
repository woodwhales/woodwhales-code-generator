package org.woodwhales.generator.core.controller.request;

import lombok.Data;

/**
 * @author woodwhales on 22.8.12 13:43
 */
@Data
public class JavaCodeOrmConfig {

    /**
     * orm框架
     */
    private String orm;

    /**
     * 是否生成BatchMapper
     */
    private Boolean generateBatchMapper;

}
