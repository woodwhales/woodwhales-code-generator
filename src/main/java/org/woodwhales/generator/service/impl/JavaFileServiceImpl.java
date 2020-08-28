package org.woodwhales.generator.service.impl;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.woodwhales.generator.entity.TableInfo;
import org.woodwhales.generator.model.GenerateInfo;
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
	public boolean process(GenerateInfo generateInfo) throws Exception {
		if(!generateInfo.getGenerateCode()) {
			return true;
		}

		HashMap<String, Object> dataModel = new HashMap<>(16);
		dataModel.put("settings", generateInfo.getDataBaseInfo());
		dataModel.put("packageName", generateInfo.getPackageName());
		String targetFilePath = generateInfo.getJavaFile().getAbsolutePath();

		final Boolean isCoverOldFile = generateInfo.getOverCode();

		for (TableInfo tableInfo : generateInfo.getTables()) {
			dataModel.put("table", tableInfo);

			entityProcess(dataModel, targetFilePath, tableInfo.getName(), isCoverOldFile, generateInfo);
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

			process(dataModel, targetFilePath, "controller", tableInfo.getName()+"Controller", isCoverOldFile);
			process(dataModel, targetFilePath, "service", tableInfo.getName()+"Service", isCoverOldFile);
			process(dataModel, targetFilePath, "service.impl", tableInfo.getName()+"ServiceImpl", isCoverOldFile);
		}
		return true;
	}

	private boolean entityProcess(HashMap<String, Object> dataModel, String targetFilePath,
								  String fileName, Boolean isCoverOldFile, GenerateInfo generateInfo) throws Exception {

		if(generateInfo.hasSuperClass()) {
			dataModel.put("hasSuperClass", generateInfo.hasSuperClass());
			dataModel.put("superClass", generateInfo.getSuperClass());
			dataModel.put("superClassSimpleName", generateInfo.getSuperClassSimpleName());
		}

		if(generateInfo.hasInterfaceList()) {
			dataModel.put("hasInterfaceList", generateInfo.hasInterfaceList());
			dataModel.put("interfaceSimpleNameListString", generateInfo.getInterfaceSimpleNameListString());
			dataModel.put("interfaceList", generateInfo.getInterfaceList());
		}

		return process(dataModel, targetFilePath, "entity", fileName, isCoverOldFile);
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
