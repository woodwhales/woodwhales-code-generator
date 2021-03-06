package org.woodwhales.generator.plugin.controller.request.template;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author woodwhales
 */
@Data
@NoArgsConstructor
public class SearchInput {

    private String name;
    private String id;
    private String placeholder;
    private Integer sort;

}