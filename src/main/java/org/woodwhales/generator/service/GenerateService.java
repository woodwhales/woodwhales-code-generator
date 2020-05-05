package org.woodwhales.generator.service;

import java.util.List;

import org.woodwhales.generator.entity.DataBaseInfo;
import org.woodwhales.generator.entity.TableInfo;

public interface GenerateService {

	List<String> listSchema(DataBaseInfo dataBaseInfo) throws Exception;

	List<TableInfo> listTables(DataBaseInfo dataBaseInfo) throws Exception;
	
}
