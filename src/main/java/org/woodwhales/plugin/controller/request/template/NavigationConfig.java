package org.woodwhales.plugin.controller.request.template;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NavigationConfig {

    private Tab tab;
    private Cite cite;
    private int sort;

}