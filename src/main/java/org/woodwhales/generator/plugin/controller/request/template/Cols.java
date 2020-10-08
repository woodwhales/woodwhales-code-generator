package org.woodwhales.generator.plugin.controller.request.template;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author woodwhales
 */
@Data
@NoArgsConstructor
public class Cols {

    private String field;
    private String title;
    private Integer width;
    private Integer sort;
}