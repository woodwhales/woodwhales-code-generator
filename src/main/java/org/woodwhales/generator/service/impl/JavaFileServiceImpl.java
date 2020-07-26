package org.woodwhales.generator.service.impl;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.woodwhales.generator.entity.DataBaseInfo;
import org.woodwhales.generator.entity.TableInfo;
import org.woodwhales.generator.service.FreeMarkerService;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;

/**
 * 模板接口实现
 * @author woodwhales
 */
@Slf4j
@Service("javaFileService")
public class JavaFileServiceImpl extends BaseFeeMarkerService implements FreeMarkerService {
	
	private Configuration configuration;
	
	@Autowired
    ResourceLoader resourceLoader;
	
	/**
	 * 创建配置类
	 */
	@PostConstruct
	public void init() {
		configuration = getConfiguration(resourceLoader);
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

	@Override
	protected String filePath() {
		return "classpath:template";
	}
}
