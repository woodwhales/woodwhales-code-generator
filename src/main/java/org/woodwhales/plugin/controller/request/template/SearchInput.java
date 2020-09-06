package org.woodwhales.plugin.controller.request.template;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchInput {

    private String name;
    private String id;
    private String placeholder;
    private int sort;

}