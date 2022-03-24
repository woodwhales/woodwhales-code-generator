package ${packageName}.mapper;

import org.apache.ibatis.annotations.Mapper;
import ${packageName}.entity.${table.name};

/**
 * ${table.name}Mapper
<#if settings.author??>
 *
 * @author ${settings.author} on ${settings.now}
</#if>
 *
 */
@Mapper
public interface ${table.name}Mapper {

}