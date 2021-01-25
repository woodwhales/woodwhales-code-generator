package ${packageName}.batchmapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import ${packageName}.entity.${table.name};
import ${packageName}.mapper.${table.name}Mapper;

/**
 * Batch${table.name}Mapper
<#if settings.author??>
 *
 * @author ${settings.author} on ${settings.now}
</#if>
 *
 */
@Service
public class Batch${table.name}Mapper extends ServiceImpl<${table.name}Mapper, ${table.name}> {
    
}