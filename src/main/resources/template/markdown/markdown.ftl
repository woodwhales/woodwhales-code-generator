# ${schema}数据库表结构设计

<#list tables as table>
## ${table.name}表(${table.dbName})
> ${table.comment!''}

| 名称 | 数据类型 | 长度 | 允许NULL | 默认值 | 注释 | 备注说明 |
| ---- | -------- | ---- | -------- | ------ | ---- | -------- |
<#list table.columns as column>
| ${column.dbName} | ${column.dbType} | ${column.columnSize!''} | ${column.nullableString} | ${column.defaultValue!''} | ${column.comment!''} |  |
</#list>

</#list>
