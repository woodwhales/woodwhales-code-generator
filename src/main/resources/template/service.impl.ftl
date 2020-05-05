package ${packageName}.service.impl;
<#assign mapper="${table.name?uncap_first}Mapper">

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ${packageName}.entity.${table.name};
import ${packageName}.service.${table.name}Service;
import ${packageName}.mapper.${table.name}Mapper;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class ${table.name}ServiceImpl implements ${table.name}Service {

    @Autowired
    private ${table.name}Mapper ${mapper};
    
    @Override
    public ${table.name} get${table.name}By${primaryKey?cap_first}(${primaryKeyType} ${primaryKey}) {
        return ${mapper}.selectOne(Wrappers.<${table.name}>lambdaQuery().eq(${table.name}::get${primaryKey?cap_first}, ${primaryKey}));
    }
    
}