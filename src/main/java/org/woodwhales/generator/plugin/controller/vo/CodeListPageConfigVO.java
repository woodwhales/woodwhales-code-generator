package org.woodwhales.generator.plugin.controller.vo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.woodwhales.generator.plugin.controller.request.template.ListPageConfig;
import org.woodwhales.generator.plugin.controller.request.template.SearchInput;
import org.woodwhales.generator.plugin.entity.CodeListPageConfig;

import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * @projectName: woodwhales-code-generator
 * @author: woodwhales
 * @date: 20.9.8 22:35
 * @description:
 */
@Data
@NoArgsConstructor
public class CodeListPageConfigVO {

    private Integer id;

    private String configName;

    private String description;

    private String configContent;

    private ListPageConfig listPageConfigs;

    public static CodeListPageConfigVO build(CodeListPageConfig codeListPageConfig) {
        CodeListPageConfigVO codeListPageConfigVO = new CodeListPageConfigVO();
        BeanUtils.copyProperties(codeListPageConfig, codeListPageConfigVO);
        ListPageConfig listPageConfigs = new Gson().fromJson(codeListPageConfig.getConfigContent(), new TypeToken<ListPageConfig>() {
        }.getType());

        listPageConfigs.setSearchInputs(listPageConfigs.getSearchInputs()
                                                    .stream()
                                                    .sorted(Comparator.comparing(SearchInput::getSort))
                                                    .collect(Collectors.toList()));

        codeListPageConfigVO.setListPageConfigs(listPageConfigs);
        return codeListPageConfigVO;
    }
}
