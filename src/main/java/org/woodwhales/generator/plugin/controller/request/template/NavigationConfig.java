package org.woodwhales.generator.plugin.controller.request.template;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NavigationConfig {

    private Tab tab;
    private Cite cite;
    private Integer sort;

}