package org.woodwhales.generator.plugin.controller.request.template;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author woodwhales
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NavigationConfig {

    private String dbTableName;
    private Tab tab;
    private Cite cite;
    private Integer sort;

}