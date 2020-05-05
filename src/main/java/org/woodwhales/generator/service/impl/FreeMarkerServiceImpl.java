package org.woodwhales.generator.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.woodwhales.generator.entity.DataBaseInfo;
import org.woodwhales.generator.entity.TableInfo;
import org.woodwhales.generator.service.FreeMarkerService;

import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FreeMarkerServiceImpl implements FreeMarkerService {
	
	private Configuration configuration;
	
	@Autowired
    ResourceLoader resourceLoader;
	
	/**
	 * 创建配置类
	 */
	@PostConstruct
	public void init() {
		configuration = new Configuration(Configuration.VERSION_2_3_22);
		configuration.setDefaultEncoding("UTF-8");
		configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		Resource resource = resourceLoader.getResource("classpath:template");
		File file;
		try {
			file = resource.getFile();
			FileTemplateLoader templateLoader = new FileTemplateLoader(file);
			configuration.setTemplateLoader(templateLoader);
		} catch (IOException e) {
			log.error("load template process is failed, {}", e);
		}
	}

	@Override
	public void process(File targetFile, String packageName, DataBaseInfo dataBaseInfo, List<TableInfo> tables) throws Exception {
		HashMap<String, Object> dataModel = new HashMap<>();
		dataModel.put("settings", dataBaseInfo);
		dataModel.put("packageName", packageName);
		String targetFilePath = targetFile.getAbsolutePath();
		
		for (TableInfo tableInfo : tables) {
			dataModel.put("table", tableInfo);
			process(dataModel, tableInfo, targetFilePath, "entity", tableInfo.getName());
			process(dataModel, tableInfo, targetFilePath, "mapper", tableInfo.getName()+"Mapper");

			dataModel.put("primaryKey", tableInfo.getKeys().get(0));
			dataModel.put("primaryKeyType", tableInfo.getKeyTypes().get(0));
			process(dataModel, tableInfo, targetFilePath, "controller", tableInfo.getName()+"Controller");
			process(dataModel, tableInfo, targetFilePath, "service", tableInfo.getName()+"Service");
			process(dataModel, tableInfo, targetFilePath, "service.impl", tableInfo.getName()+"ServiceImpl");
		}
		
	}
	
	private void process(HashMap<String, Object> dataModel, TableInfo tableInfo, String targetFilePath, String templateName, String fileName) throws Exception {
		Template template = configuration.getTemplate(templateName + ".ftl");
		
		String[] split = StringUtils.split(templateName, ".");
		StringBuffer sb = new StringBuffer(targetFilePath);
		for (String str : split) {
			sb.append(File.separator);
			sb.append(str);
		}
		String modelPath = sb.toString();
		FileUtils.forceMkdir(new File(modelPath));
		// 是否覆盖原来的文件
		boolean isCoverOldFile = true;
		FileWriter fw = new FileWriter(new File(modelPath + File.separator + fileName +".java"), !isCoverOldFile);
		template.process(dataModel, fw);
	}

}
