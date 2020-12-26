package ${packageName}.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ${packageName}.entity.${table.name};

/**
 * ${table.name}Mapper
<#if settings.author??>
 *
 * @author ${settings.author} on ${settings.now}
</#if>
 *
 */
public interface ${table.name}Mapper extends BaseMapper<${table.name}> {
    
}