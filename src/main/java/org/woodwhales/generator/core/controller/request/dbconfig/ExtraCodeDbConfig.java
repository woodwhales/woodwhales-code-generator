package org.woodwhales.generator.core.controller.request.dbconfig;

import lombok.Data;

/**
 * @author woodwhales on 2021-08-01 21:41
 * @Description
 */
@Data
public class ExtraCodeDbConfig {
    private boolean generateCode;
    private boolean overCode;
    private boolean overMarkdown;
    private boolean generateMarkdown;
}
