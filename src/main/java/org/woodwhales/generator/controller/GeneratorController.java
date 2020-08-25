package org.woodwhales.generator.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.woodwhales.generator.controller.request.DataBaseRequestBody;
import org.woodwhales.generator.controller.response.RespVO;
import org.woodwhales.generator.entity.DataBaseInfo;
import org.woodwhales.generator.model.GenerateInfo;
import org.woodwhales.generator.service.FreeMarkerService;
import org.woodwhales.generator.service.GenerateInfoFactory;
import org.woodwhales.generator.service.GenerateService;

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

	@Autowired
	private GenerateInfoFactory generateInfoFactory;

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
		return RespVO.success(generateService.listTables(dataBaseInfo, false));
	}
	
	@PostMapping("/process")
	public RespVO process(@RequestBody DataBaseRequestBody dataBaseRequestBody) throws Exception {
		log.info("dataBaseRequestBody = {}", dataBaseRequestBody);

		GenerateInfo generateInfo = generateInfoFactory.build(dataBaseRequestBody);

		boolean generateCodeSuccess;
		boolean generateMarkdownSuccess;
		// 生成代码
		if (dataBaseRequestBody.getGenerateCode()) {
			generateCodeSuccess = javaFileService.process(generateInfo);
		} else {
			generateCodeSuccess = true;
		}

		// 生成markdown
		if (dataBaseRequestBody.getGenerateMarkdown()) {
			generateMarkdownSuccess = markdownService.process(generateInfo);
		} else {
			generateMarkdownSuccess = true;
		}

		return RespVO.resp(generateCodeSuccess && generateMarkdownSuccess);
	}

}
