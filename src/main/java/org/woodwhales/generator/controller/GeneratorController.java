package org.woodwhales.generator.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.woodwhales.generator.controller.request.DataBaseRequestBody;
import org.woodwhales.generator.controller.response.RespVO;
import org.woodwhales.generator.entity.DataBaseInfo;
import org.woodwhales.generator.entity.TableInfo;
import org.woodwhales.generator.exception.GenerateException;
import org.woodwhales.generator.service.FreeMarkerService;
import org.woodwhales.generator.service.GenerateService;

import java.io.File;
import java.util.List;

/**
 * 生成代码相关接口
 * @author woodwhales
 */
@Slf4j
@RestController
@RequestMapping("/generate")
public class GeneratorController {
	
	@Autowired
	private GenerateService generateService;
	
	@Autowired
	@Qualifier("javaFileService")
	private FreeMarkerService javaFileService;

	@Autowired
	@Qualifier("markdownService")
	private FreeMarkerService markdownService;

	/**
	 * 通过数据基本链接信息，获取数据库的元信息
	 * @param dataBaseRequestBody
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/buildConnection")
	public RespVO buildConnection(@RequestBody DataBaseRequestBody dataBaseRequestBody) throws Exception {
		log.info("dataBaseRequestBody = {}", dataBaseRequestBody);
		DataBaseInfo dataBaseInfo = DataBaseInfo.convert(dataBaseRequestBody);
		return RespVO.success(generateService.listSchema(dataBaseInfo));
	}

	/**
	 * 根据指定的schema生成数据对象信息
	 * @param dataBaseRequestBody
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/buildTableInfo")
	public RespVO buildTableInfo(@RequestBody DataBaseRequestBody dataBaseRequestBody) throws Exception {
		log.info("dataBaseRequestBody = {}", dataBaseRequestBody);
		DataBaseInfo dataBaseInfo = DataBaseInfo.convert(dataBaseRequestBody);
		return RespVO.success(generateService.listTables(dataBaseInfo));
	}
	
	@PostMapping("/process")
	public RespVO process(@RequestBody DataBaseRequestBody dataBaseRequestBody) throws Exception {
		log.info("dataBaseRequestBody = {}", dataBaseRequestBody);
		// 检查目标文件目录是否为合法文件夹
		String baseDirPath = dataBaseRequestBody.getGenerateDir();
		DataBaseInfo dataBaseInfo = DataBaseInfo.convert(dataBaseRequestBody);
		List<TableInfo> tables = generateService.listTables(dataBaseInfo);

		String packageName = dataBaseRequestBody.getPackageName();

		boolean generateCodeSuccess;
		boolean generateMarkdownSuccess;
		// 生成代码
		if (dataBaseRequestBody.getGenerateCode()) {
			File baseDir = checkBaseDirPath(baseDirPath);
			File targetFile = getTargetFile(baseDir.getAbsolutePath(), packageName);
			generateCodeSuccess = javaFileService.process(targetFile, packageName, dataBaseInfo, tables);
		} else {
			generateCodeSuccess = true;
		}

		// 生成markdown
		if (dataBaseRequestBody.getGenerateMarkdown()) {
			generateMarkdownSuccess = markdownService.process(new File(baseDirPath), packageName, dataBaseInfo, tables);
		} else {
			generateMarkdownSuccess = true;
		}

		return RespVO.resp(generateCodeSuccess && generateMarkdownSuccess);
	}
	
	private File checkBaseDirPath(String baseDirPath) {
		File tmpFile = FileUtils.getFile(baseDirPath + File.separator + "src" + File.separator + "main" + File.separator + "java");
		if(!tmpFile.exists()) {
			try {
				FileUtils.forceMkdir(tmpFile);
			} catch (Exception e) {
				log.error("create dir process failed, {}", e);
				throw new GenerateException("生成代码的目录失败");
			}
		}

		return tmpFile;
	}

	private File getTargetFile(String baseDirPath, String packageName) {
		String[] dirNames = StringUtils.split(packageName, ".");
		StringBuffer sb = new StringBuffer();
		for (String dirName : dirNames) {
			sb.append(File.separator + dirName);
		}
		String packageDir = sb.toString();
		
		String targetDir =  baseDirPath + File.separator + packageDir;
		File targetDirFile = new File(targetDir);
		if(!targetDirFile.exists()) {
			try {
				FileUtils.forceMkdir(targetDirFile);
			} catch (Exception e) {
				log.error("create dir process failed, {}", e);
				throw new GenerateException("生成代码的包目录失败");
			}
		}

		return targetDirFile;
	}
}
