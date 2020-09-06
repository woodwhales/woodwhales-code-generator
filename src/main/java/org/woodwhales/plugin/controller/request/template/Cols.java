package org.woodwhales.plugin.controller.request.template;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Cols {

    private String field;
    private String title;
    private int width;
    private int sort;
}