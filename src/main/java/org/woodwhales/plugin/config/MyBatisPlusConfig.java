package org.woodwhales.plugin.config;

import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @projectName: woodwhales-code-generator
 * @author: woodwhales
 * @date: 20.9.5 14:39
 * @description:
 */
@Configuration
@MapperScan("org.woodwhales.plugin.mapper")
public class MyBatisPlusConfig {

    @Bean
    public PaginationInnerInterceptor paginationInterceptor() {
        PaginationInnerInterceptor paginationInterceptor = new PaginationInnerInterceptor();
        // 单页分页条数限制
        paginationInterceptor.setMaxLimit(500L);
        return paginationInterceptor;
    }

}
