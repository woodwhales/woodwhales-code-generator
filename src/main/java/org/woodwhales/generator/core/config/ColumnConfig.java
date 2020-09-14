package org.woodwhales.generator.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 列配置
 * @author woodwhales
 */
@Data
@Component
@ConfigurationProperties(prefix = "column")
@PropertySource(value = "classpath:typeConverter.properties")
public class ColumnConfig {
	
	private Map<String, String> type = new HashMap<>();
	
}
