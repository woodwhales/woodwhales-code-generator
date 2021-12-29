package org.woodwhales.generator.core.service;

import cn.woodwhales.common.model.result.OpResult;
import cn.woodwhales.common.model.vo.PageRespVO;
import org.woodwhales.generator.core.controller.request.CodeDatabaseConfigQueryParam;
import org.woodwhales.generator.core.controller.request.dbconfig.CodeDatabaseConfigGetRequestBody;
import org.woodwhales.generator.core.controller.request.dbconfig.CodeDatabaseConfigRequestBody;
import org.woodwhales.generator.core.controller.vo.CodeDatabaseConfigVO;

/**
 * @projectName: woodwhales-code-generator
 * @author: woodwhales
 * @date: 21.5.20 23:21
 * @description:
 */
public interface CodeDatabaseConfigService {

    /**
     * 条件分页查询数据库配置信息
     * @param param
     * @return
     */
    PageRespVO<CodeDatabaseConfigVO> page(CodeDatabaseConfigQueryParam param);

    /**
     * 创建数据库配置信息
     * @param requestBody
     * @return
     */
    OpResult<Void> create(CodeDatabaseConfigRequestBody requestBody);

    /**
     * 获取数据库配置信息
     * @param requestBody
     * @return
     */
    OpResult<CodeDatabaseConfigVO> get(CodeDatabaseConfigGetRequestBody requestBody);
}
