package org.woodwhales.generator.core.controller.request;

import lombok.Data;

import java.util.List;

/**
 * @author woodwhales on 22.8.12 13:41
 */
@Data
public class JavaCodeConfig {

    /**
     * 是否生成代码
     */
    private Boolean generateCode;

    /**
     * 是否覆盖代码
     */
    private Boolean overCode;

    /**
     * 生成代码的目录
     */
    private String generateDir;

    /**
     * 包名称
     */
    private String packageName;

    /**
     * 是否生成controller
     */
    private Boolean generateController;

    /**
     * 是否生成service
     */
    private Boolean generateService;

    /**
     * 父类
     */
    private String superClass;

    /**
     * 接口
     */
    private List<String> interfaceList;

    /**
     * 作者名称
     */
    private String author;

    /**
     * orm 框架选择
     */
    private JavaCodeOrmConfig ormConfig;

}
