package org.woodwhales.generator.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 表配置
 * @author woodwhales
 */
@Data
@Component
@ConfigurationProperties(prefix = "table")
@PropertySource(value = "classpath:tablePrefix.properties")
public class TableConfig {

	private List<String> prefix = new ArrayList<>();
	
}
