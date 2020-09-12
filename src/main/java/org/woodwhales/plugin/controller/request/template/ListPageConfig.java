package org.woodwhales.plugin.controller.request.template;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@NoArgsConstructor
public class ListPageConfig {

    @NotBlank(message = "导航栏名称不允许为空")
    private String navName;

    private List<SearchInput> searchInputs;

    private TableConfig tableConfig;
}