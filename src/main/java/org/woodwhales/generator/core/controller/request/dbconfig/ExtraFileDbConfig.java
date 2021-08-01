package org.woodwhales.generator.core.controller.request.dbconfig;

import lombok.Data;

/**
 * @author woodwhales on 2021-08-01 21:41
 * @Description
 */
@Data
public class ExtraFileDbConfig {
    private boolean generateController;
    private boolean generateService;
    private boolean generateBatchMapper;
}
