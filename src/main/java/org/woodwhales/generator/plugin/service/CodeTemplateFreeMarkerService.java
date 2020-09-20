package org.woodwhales.generator.plugin.service;

import org.woodwhales.generator.plugin.model.CodeTemplateConfigDetail;

/**
 * @author woodwhales on 2020-09-14
 * @description
 */
public interface CodeTemplateFreeMarkerService {

    /**
     * 生成模板
     * @param codeTemplateConfigDetail
     * @return
     * @throws Exception
     */
    boolean process(CodeTemplateConfigDetail codeTemplateConfigDetail) throws Exception;

}
