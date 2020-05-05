package org.woodwhales.generator.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "column")
@PropertySource(value = "classpath:typeConverter.properties")
public class ColumnConfig {
	
	private Map<String, String> type = new HashMap<>();
	
}
