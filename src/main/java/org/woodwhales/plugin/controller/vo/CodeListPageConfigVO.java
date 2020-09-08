package org.woodwhales.plugin.controller.vo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.woodwhales.plugin.controller.request.template.ListPageConfig;
import org.woodwhales.plugin.entity.CodeListPageConfig;

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

    private ListPageConfig listPageConfig;

    public static CodeListPageConfigVO build(CodeListPageConfig codeListPageConfig) {
        CodeListPageConfigVO codeListPageConfigVO = new CodeListPageConfigVO();
        BeanUtils.copyProperties(codeListPageConfig, codeListPageConfigVO);
        codeListPageConfigVO.setListPageConfig(new Gson().fromJson(codeListPageConfig.getConfigContent(), new TypeToken<ListPageConfig>() {}.getType()));
        return codeListPageConfigVO;
    }
}
