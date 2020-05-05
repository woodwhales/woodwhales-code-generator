package ${packageName}.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * ${table.comment}
 * 
 */
@TableName(value= "${table.dbName}")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ${table.name} implements Serializable {
    
    private static final long serialVersionUID = 1L;

    <#list table.columns as column>
    /**
     * ${column.comment}
     */
    <#if column.primaryKey>
    @TableId(type = IdType.AUTO)
    private ${column.type} ${column.name};
    <#else>
    private ${column.type} ${column.name};
    </#if>

    </#list>
}