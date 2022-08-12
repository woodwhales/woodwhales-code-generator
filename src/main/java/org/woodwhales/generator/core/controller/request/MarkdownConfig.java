package org.woodwhales.generator.core.controller.request;

import lombok.Data;

/**
 * @author woodwhales on 22.8.12 13:46
 */
@Data
public class MarkdownConfig {

    /**
     * 是否生成数据库表结构设计文档
     */
    private Boolean generateMarkdown;

    /**
     * markdown 生成的目录
     */
    private String markdownDir;

    /**
     * 是否覆盖markdown
     */
    private Boolean overMarkdown;

}
