package org.woodwhales.generator.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.woodwhales.generator.controller.request.BuildConnectionRequestBody;
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
	 * @param requestBody
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/buildConnection")
	public RespVO buildConnection(@RequestBody BuildConnectionRequestBody requestBody) throws Exception {
		log.info("buildConnectionRequestBody = {}", requestBody);
		DataBaseInfo dataBaseInfo = DataBaseInfo.convert(requestBody);
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

	/**
	 * 生成代码或文档
	 * @param dataBaseRequestBody
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/process")
	public RespVO process(@RequestBody DataBaseRequestBody dataBaseRequestBody) throws Exception {
		log.info("dataBaseRequestBody = {}", dataBaseRequestBody);

		GenerateInfo generateInfo = generateInfoFactory.build(dataBaseRequestBody);

		// 生成代码
		boolean generateCodeSuccess = javaFileService.process(generateInfo);

		// 生成markdown
		boolean generateMarkdownSuccess = markdownService.process(generateInfo);

		return RespVO.resp(generateCodeSuccess && generateMarkdownSuccess);
	}

}
