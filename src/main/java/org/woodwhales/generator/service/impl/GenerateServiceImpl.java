package org.woodwhales.generator.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woodwhales.generator.config.ColumnConfig;
import org.woodwhales.generator.config.TableConfig;
import org.woodwhales.generator.entity.Column;
import org.woodwhales.generator.entity.DataBaseInfo;
import org.woodwhales.generator.entity.TableInfo;
import org.woodwhales.generator.exception.GenerateException;
import org.woodwhales.generator.service.GenerateService;
import org.woodwhales.generator.util.StringTools;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class GenerateServiceImpl implements GenerateService {
	
	@Autowired
	private ColumnConfig columnConfig;
	
	@Autowired
	private TableConfig tableConfig;

	@Override
	public Connection getConnection(DataBaseInfo dataBaseInfo) throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(dataBaseInfo.getUrl(), dataBaseInfo.getProperties());
		} catch (SQLException exception) {
			log.error("cause by : {}", exception.getMessage(), exception);
			StringUtils.equals("28000", exception.getSQLState());
			throw new GenerateException("数据库链接失败，账号或密码不正确！");
		}

		return connection;
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

		final String catalog = connection.getCatalog();

		DatabaseMetaData metaData = connection.getMetaData();
		final String schema = dataBaseInfo.getSchema();

		ResultSet resultSet = metaData.getTables(schema, null, null, new String[] {"TABLE"});
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
			
			List<String> primaryKeys = getPrimaryKey(metaData, catalog, schema, tableName);
			
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

	/**
	 * 获取当前数据库表的所有字段信息
	 * @param metaData
	 * @param schema
	 * @param tableInfo
	 * @return
	 * @throws Exception
	 */
	private List<Column> getColumns(DatabaseMetaData metaData, String schema, TableInfo tableInfo) throws Exception {
		ResultSet resultSet = metaData.getColumns(schema, null, tableInfo.getDbName(), null);
		List<Column> columns = new ArrayList<>();
		while (resultSet.next()) {
			String columnName = resultSet.getString("COLUMN_NAME");
			String typeName = resultSet.getString("TYPE_NAME");
			String remarks = resultSet.getString("REMARKS");
			String defaultValue = resultSet.getString("COLUMN_DEF");
			int columnSize = resultSet.getInt("COLUMN_SIZE");
			boolean nullable = BooleanUtils.toBoolean(resultSet.getInt("NULLABLE"));
			String nullableString = BooleanUtils.toString(nullable, "是", "否");

			Column column = Column.builder()
								  .dbName(columnName)
								  .dbType(typeName)
								  .nullAble(nullable)
					              .nullableString(nullableString)
								  .columnSize(columnSize)
								  .defaultValue(defaultValue)
								  .comment(remarks).build();

			// 将数据库表的字段类型转成 java 变量类型
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

	private List<String> getPrimaryKey(DatabaseMetaData metaData, String catalog, String schema, String tableName) throws Exception {
		ResultSet primaryKeys = metaData.getPrimaryKeys(catalog, schema, tableName);

		List<String> keys = new ArrayList<>();
		while (primaryKeys.next()) {
			String keyName = primaryKeys.getString("COLUMN_NAME");
			keys.add(keyName);
		}
		
		return keys;
	}

}
