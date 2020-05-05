package org.woodwhales.generator.service;

import java.io.File;
import java.util.List;

import org.woodwhales.generator.entity.DataBaseInfo;
import org.woodwhales.generator.entity.TableInfo;

public interface FreeMarkerService {

	void process(File targetFile, String packageName, DataBaseInfo dataBaseInfo, List<TableInfo> tables) throws Exception;
	
}
