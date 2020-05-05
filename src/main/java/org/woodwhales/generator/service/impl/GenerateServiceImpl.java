package org.woodwhales.generator.service.impl;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woodwhales.generator.config.ColumnConfig;
import org.woodwhales.generator.config.TableConfig;
import org.woodwhales.generator.entity.Column;
import org.woodwhales.generator.entity.DataBaseInfo;
import org.woodwhales.generator.entity.TableInfo;
import org.woodwhales.generator.service.GenerateService;
import org.woodwhales.generator.util.StringTools;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GenerateServiceImpl implements GenerateService {
	
	@Autowired
	private ColumnConfig columnConfig;
	
	@Autowired
	private TableConfig tableConfig;

	private Connection getConnection(DataBaseInfo dataBaseInfo) throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
		return DriverManager.getConnection(dataBaseInfo.getUrl(), dataBaseInfo.getProperties());
	}

	@Override
	public List<String> listSchema(DataBaseInfo dataBaseInfo) throws Exception {
		Connection connection = getConnection(dataBaseInfo);
		DatabaseMetaData metaData = connection.getMetaData();
		ResultSet resultSet = metaData.getCatalogs();
		
		List<String> schemaList = new ArrayList<>();
		while (resultSet.next()) {
			schemaList.add(resultSet.getString(1));
		}
		resultSet.close();
		connection.close();
		return schemaList;
	}

	@Override
	public List<TableInfo> listTables(DataBaseInfo dataBaseInfo) throws Exception {
		Connection connection = getConnection(dataBaseInfo);
		DatabaseMetaData metaData = connection.getMetaData();
		ResultSet resultSet = metaData.getTables(dataBaseInfo.getSchema(), null, null, new String[] {"TABLE"});
		
		List<TableInfo> tableInfos = new ArrayList<>();
		while (resultSet.next()) {
			String tableName = resultSet.getString("TABLE_NAME");
			TableInfo tableInfo = TableInfo.builder()
									.dbName(tableName)
									.comment(resultSet.getString("REMARKS"))
									.build();
			
			String tempTableName = tableName;
			// 格式化表名
			tempTableName = StringTools.substringAfter(tempTableName, tableConfig.getPrefix());
			
			tableInfo.setName(StringTools.upper(tempTableName));
			
			List<String> primaryKeys = getPrimaryKey(metaData, tableName);
			
			tableInfo.setKeys(primaryKeys);
			
			List<Column> columns = getColumns(metaData, dataBaseInfo.getSchema(), tableInfo);
			tableInfo.setColumns(columns);
			tableInfo.setKeyTypes(getPrimaryKeyTypes(primaryKeys, columns));
			tableInfos.add(tableInfo);
		}
		
		return tableInfos;
	}
	
	private List<String> getPrimaryKeyTypes(List<String> primaryKeys, List<Column> columns) {
		List<String> primaryKeyTypes = new ArrayList<>();
		for (String primaryKey : primaryKeys) {
			for (Column column : columns) {
				if(column.isPrimaryKey() && column.getName().equals(primaryKey)) {
					primaryKeyTypes.add(column.getType());
				}
			}
			
		}
		
		return primaryKeyTypes;
	}

	private List<Column> getColumns(DatabaseMetaData metaData, String schema, TableInfo tableInfo) throws Exception {
		ResultSet resultSet = metaData.getColumns(schema, null, tableInfo.getDbName(), null);
		List<Column> columns = new ArrayList<>();
		while (resultSet.next()) {
			String columnName = resultSet.getString("COLUMN_NAME");
			Column column = Column.builder()
								  .dbName(columnName)
								  .dbType(resultSet.getString("TYPE_NAME"))
								  .comment(resultSet.getString("REMARKS")).build();
			
			column.setType(convertType(column.getDbType()));
			column.setName(StringTools.upperWithOutFisrtChar(columnName));
			column.setPrimaryKey(checkPrimaryKey(columnName, tableInfo.getKeys()));
			
			columns.add(column);
		}
		
		return columns;
	}
	
	private boolean checkPrimaryKey(String columnName, List<String> keys) {
		if(CollectionUtils.isEmpty(keys)) {
			return false;
		}
		return keys.contains(columnName);
	}

	private String convertType(String dbType) {
		Map<String, String> typeMap = columnConfig.getType();
		String type = typeMap.get(dbType);
		
		if(StringUtils.isBlank(type)) {
			for(String key : typeMap.keySet()){
				if(StringUtils.containsIgnoreCase(dbType, key)) {
					return typeMap.get(key);
				}
			}
		}
		return type;
	}

	private List<String> getPrimaryKey(DatabaseMetaData metaData, String tableName) throws Exception {
		ResultSet primaryKeys = metaData.getPrimaryKeys(null, null, tableName);

		List<String> keys = new ArrayList<>();
		while (primaryKeys.next()) {
			String keyName = primaryKeys.getString("COLUMN_NAME");
			keys.add(keyName);
		}
		
		return keys;
	}

}
