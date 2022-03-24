package ${packageName}.service;

import java.util.List;

import ${packageName}.entity.${table.name};

/**
 * ${table.name}Service
<#if settings.author??>
 *
 * @author ${settings.author} on ${settings.now}
</#if>
 *
 */
public interface ${table.name}Service {

<#if primaryKeyType??>
    /**
    *  根据id查询${table.name}
    */
    ${table.name} get${table.name}ById(${primaryKeyType} ${primaryKey});
</#if>

}