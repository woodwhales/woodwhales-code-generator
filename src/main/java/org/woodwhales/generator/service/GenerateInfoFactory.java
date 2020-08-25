package org.woodwhales.generator.service;

import org.woodwhales.generator.controller.request.DataBaseRequestBody;
import org.woodwhales.generator.model.GenerateInfo;

/**
 * @projectName: woodwhales-code-generator
 * @author: woodwhales
 * @date: 20.8.25 22:57
 * @description:
 */
public interface GenerateInfoFactory {

    /**
     * 生成 GenerateInfo
     * @see GenerateInfo
     *
     * @param dataBaseRequestBody
     * @return
     * @throws Exception
     */
    GenerateInfo build(DataBaseRequestBody dataBaseRequestBody) throws Exception;
}
