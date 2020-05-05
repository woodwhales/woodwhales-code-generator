package ${packageName}.service;

import java.util.List;

import ${packageName}.entity.${table.name};

public interface ${table.name}Service {

    /**
     *  根据id查询${table.name}
     */
    ${table.name} get${table.name}ById(${primaryKeyType} ${primaryKey});
    
}