package org.woodwhales.generator.core.controller.request;

import lombok.Data;
import org.woodwhales.common.model.param.PageQueryParam;

/**
 * @author woodwhales on 2021-05-20 23:24
 * @description
 */
@Data
public class CodeDatabaseConfigQueryParam extends PageQueryParam {

    /**
     * 配置名称
     */
    private String configName;

}
