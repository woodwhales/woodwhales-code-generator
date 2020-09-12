package org.woodwhales.generator.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.woodwhales.common.response.PageRespVO;
import org.woodwhales.common.response.RespVO;
import org.woodwhales.generator.controller.request.BuildConnectionRequestBody;
import org.woodwhales.generator.controller.request.DataBaseRequestBody;
import org.woodwhales.generator.controller.request.DataBaseTableRequestBody;
import org.woodwhales.generator.controller.request.GenerateTemplateRequestBody;
import org.woodwhales.generator.controller.vo.ColsConfigVO;
import org.woodwhales.generator.controller.vo.NavigationConfigVO;
import org.woodwhales.generator.entity.Column;
import org.woodwhales.generator.entity.DataBaseInfo;
import org.woodwhales.generator.entity.TableInfo;
import org.woodwhales.generator.model.GenerateTableInfos;
import org.woodwhales.generator.service.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.*;

/**
 * 生成代码相关接口
 * @author woodwhales
 */
@Slf4j
@RestController()
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
	private DynamicFreeMarkerService dynamicFreeMarkerService;

	@Autowired
	private GenerateInfoFactory generateInfoFactory;

	@Autowired
	private DataBaseInfoCache dataBaseInfoCache;

	/**
	 * 通过数据基本链接信息，获取数据库的元信息
	 * @param requestBody
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/buildConnection")
	public RespVO buildConnection(@Valid @RequestBody BuildConnectionRequestBody requestBody) throws Exception {
		log.info("[buildConnection].buildConnectionRequestBody = {}", requestBody);
		DataBaseInfo dataBaseInfo = DataBaseInfo.convert(requestBody);
		return RespVO.success(generateService.listSchema(dataBaseInfo));
	}

	/**
	 * 根据指定的schema生成数据对象信息
	 * @param dataBaseRequestBody
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/buildTableInfos")
	public RespVO buildTableInfo(@Valid @RequestBody DataBaseRequestBody dataBaseRequestBody) throws Exception {
		log.info("[buildTableInfos].dataBaseRequestBody = {}", dataBaseRequestBody);
		DataBaseInfo dataBaseInfo = DataBaseInfo.convert(dataBaseRequestBody);

		List<TableInfo> tableInfos = generateService.listTables(dataBaseInfo, false);
		Map<String, Object> result = new HashMap<>(2);
		result.put("tableInfos", tableInfos);

		// 添加加密的数据库key
		String dataBaseInfoKey = dataBaseInfo.getDataBaseInfoKey();
		String encryptedDataBaseKey = dataBaseInfoCache.getEncryptedDataBaseKey(dataBaseInfoKey);
		result.put("dataBaseInfoKey", encryptedDataBaseKey);
		return RespVO.success(result);
	}

	/**
	 * 生成代码或文档
	 * @param dataBaseRequestBody
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/process")
	public RespVO process(@Valid @RequestBody DataBaseRequestBody dataBaseRequestBody) throws Exception {
		log.info("[process].dataBaseRequestBody = {}", dataBaseRequestBody);

		GenerateTableInfos generateTableInfos = generateInfoFactory.buildGenerateTableInfos(dataBaseRequestBody);

		// 生成代码
		boolean generateCodeSuccess = javaFileService.process(generateTableInfos);

		// 生成markdown
		boolean generateMarkdownSuccess = markdownService.process(generateTableInfos);

		return RespVO.resp(generateCodeSuccess && generateMarkdownSuccess);
	}

	@PostMapping("/getTableInfo")
	public RespVO<TableInfo> getTableInfo(@Valid @RequestBody DataBaseTableRequestBody requestBody) throws Exception {
		log.info("[getTableInfo].buildConnectionRequestBody = {}", requestBody);
		TableInfo tableInfo = generateService.getTableInfo(requestBody.getTableKey());
		if(Objects.isNull(tableInfo)) {
			return RespVO.error("暂无数据");
		}
		return RespVO.success(tableInfo);
	}

	@PostMapping("/template")
	public RespVO template(@Valid @RequestBody GenerateTemplateRequestBody requestBody) throws Exception {
		TableInfo tableInfo = generateService.getTableInfo(requestBody);
		String freemarkerTemplate = requestBody.getFreemarkerTemplate();
		Map<String, Object> customKeyValueMap = requestBody.getCustomKeyValueMap();

		String outPut = dynamicFreeMarkerService.dynamicProcess(freemarkerTemplate, tableInfo, customKeyValueMap);
		if(StringUtils.isBlank(outPut)) {
			return RespVO.error("生成失败");
		}

		return RespVO.success(outPut);
	}

	@GetMapping("/listTableInfos")
	public PageRespVO<NavigationConfigVO> listTableInfos(@NotBlank @RequestParam("dataBaseInfoKey") String encryptedDataBaseInfoKey) {
		List<TableInfo> tableInfos = generateService.listTables(encryptedDataBaseInfoKey);

		int index = 1;
		List<NavigationConfigVO> navigationConfigVOList = new ArrayList<>(tableInfos.size());
		for (TableInfo tableInfo : tableInfos) {
			navigationConfigVOList.add(new NavigationConfigVO(tableInfo.getDbName(),
					tableInfo.getDbName(),
					tableInfo.getDbName(),
					"/view/" + tableInfo.getPropertyName() + "/",
					index++));
		}

		return PageRespVO.success(navigationConfigVOList);
	}

	@GetMapping("/listTableInfo")
	public PageRespVO<ColsConfigVO> listTableInfo(@NotBlank @RequestParam("tableKey") String tableKey) {
		TableInfo tableInfo = generateService.getTableInfo(tableKey);
		List<Column> columns = tableInfo.getColumns();

		int index = 1;
		List<ColsConfigVO> colsConfigVOList = new ArrayList<>(columns.size());
		for (Column column : columns) {
			colsConfigVOList.add(new ColsConfigVO(column.getDbName(), column.getName(),
					column.getComment(), null, index++));
		}

		return PageRespVO.success(colsConfigVOList);
	}
}
