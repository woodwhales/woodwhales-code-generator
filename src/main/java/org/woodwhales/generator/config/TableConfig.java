package org.woodwhales.generator.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "table")
@PropertySource(value = "classpath:tablePrefix.properties")
public class TableConfig {

	private List<String> prefix = new ArrayList<>();
	
}
