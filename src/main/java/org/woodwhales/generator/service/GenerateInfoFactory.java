package org.woodwhales.generator.service;

import org.woodwhales.generator.controller.request.DataBaseRequestBody;
import org.woodwhales.generator.model.GenerateTableInfos;

/**
 * @projectName: woodwhales-code-generator
 * @author: woodwhales
 * @date: 20.8.25 22:57
 * @description:
 */
public interface GenerateInfoFactory {

    /**
     * 生成 GenerateTableInfos
     * @see GenerateTableInfos
     *
     * @param dataBaseRequestBody
     * @return
     * @throws Exception
     */
    GenerateTableInfos buildGenerateTableInfos(DataBaseRequestBody dataBaseRequestBody) throws Exception;

}
