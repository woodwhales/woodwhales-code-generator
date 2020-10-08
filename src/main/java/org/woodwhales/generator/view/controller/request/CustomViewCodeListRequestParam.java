package org.woodwhales.generator.view.controller.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author woodwhales
 * @create 2020-10-07 18:45
 */
@Data
@NoArgsConstructor
public class CustomViewCodeListRequestParam {

    @NotNull(message = "菜单主键不允许为空")
    private Integer menuId;

    @NotNull(message = "列表配置主键不允许为空")
    private Integer codeListPageConfigId;

    private String navName;

}
