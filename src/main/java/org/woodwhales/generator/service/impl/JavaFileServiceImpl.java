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
import org.woodwhales.generator.model.GenerateTableInfos;
import org.woodwhales.generator.service.FreeMarkerService;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

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
	public boolean process(GenerateTableInfos generateTableInfos) throws Exception {
		DataBaseInfo dataBaseInfo = generateTableInfos.getDataBaseInfo();
		if(!dataBaseInfo.getGenerateCode()) {
			return true;
		}

		HashMap<String, Object> dataModel = new HashMap<>(16);
		dataModel.put("settings", dataBaseInfo);
		dataModel.put("packageName", dataBaseInfo.getPackageName());
		String targetFilePath = generateTableInfos.getJavaFile().getAbsolutePath();

		final Boolean isCoverOldFile = dataBaseInfo.getOverCode();

		for (TableInfo tableInfo : generateTableInfos.getTables()) {
			dataModel.put("table", tableInfo);
			// 生成entity
			entityProcess(dataModel, targetFilePath, tableInfo.getName(), generateTableInfos);
			// 生成mapper
			process(dataModel, targetFilePath, "mapper", tableInfo.getName()+"Mapper", isCoverOldFile);

			if(tableInfo.getKeys().size() > 0) {
				dataModel.put("primaryKey", tableInfo.getKeys().get(0));
			} else {
				dataModel.put("primaryKey", null);
			}

			if(tableInfo.getKeyTypes().size() > 0) {
				dataModel.put("primaryKeyType", tableInfo.getKeyTypes().get(0));
			} else {
				dataModel.put("primaryKeyType", null);
			}

			// 生成controller
			process(dataModel, targetFilePath, "controller", tableInfo.getName()+"Controller", isCoverOldFile);
			// 生成service
			process(dataModel, targetFilePath, "service", tableInfo.getName()+"Service", isCoverOldFile);
			// 生成service.impl
			process(dataModel, targetFilePath, "service.impl", tableInfo.getName()+"ServiceImpl", isCoverOldFile);
		}
		return true;
	}

	private boolean entityProcess(HashMap<String, Object> dataModel, String targetFilePath,
								  String fileName, GenerateTableInfos generateTableInfos) throws Exception {

		if(generateTableInfos.hasSuperClass()) {
			dataModel.put("hasSuperClass", generateTableInfos.hasSuperClass());
			dataModel.put("superClass", generateTableInfos.getDataBaseInfo().getSuperClass());
			dataModel.put("superClassSimpleName", generateTableInfos.getSuperClassSimpleName());
		}

		if(generateTableInfos.hasInterfaceList()) {
			dataModel.put("hasInterfaceList", generateTableInfos.hasInterfaceList());
			dataModel.put("interfaceSimpleNameListString", generateTableInfos.getInterfaceSimpleNameListString());
			dataModel.put("interfaceList", generateTableInfos.getDataBaseInfo().getInterfaceList());
		}

		Boolean overCode = generateTableInfos.getDataBaseInfo().getOverCode();
		return process(dataModel, targetFilePath, "entity", fileName, overCode);
	}

	private boolean process(HashMap<String, Object> dataModel, String targetFilePath,
								  String templateName, String fileName, Boolean isCoverOldFile) throws Exception {
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
		try(FileWriter fw = new FileWriter(new File(modelPath + File.separator + fileName +".java"), !isCoverOldFile)) {
			template.process(dataModel, fw);
		} catch (IOException e) {
			log.error("生成文件异常，cause = {}", e.getCause().getMessage());
			return false;
		}
		return true;
	}

	@Override
	protected String filePath() {
		return "classpath:template";
	}
}
