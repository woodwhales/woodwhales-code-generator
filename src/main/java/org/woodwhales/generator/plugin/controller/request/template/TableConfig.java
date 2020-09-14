package org.woodwhales.generator.plugin.controller.request.template;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@NoArgsConstructor
public class TableConfig {

    @NotBlank(message = "table链接不允许为空")
    private String requestUrl;

    private List<Cols> cols;
}