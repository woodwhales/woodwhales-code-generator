package ${packageName}.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
<#if hasSuperClass??>
import ${superClass};
</#if>
<#if hasInterfaceList??>
<#list interfaceList as interfaceClass>
import ${interfaceClass};
</#list>
</#if>

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
<#--import lombok.experimental.Accessors;-->
<#if hasSuperClass??>
import lombok.EqualsAndHashCode;
</#if>

/**
 * ${table.comment}
<#if settings.author??>
 *
 * @author ${settings.author} on ${settings.now}
</#if>
 *
 */
@TableName(value= "${table.dbName}")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
<#--@Accessors(chain = true)-->
<#if hasSuperClass??>
@EqualsAndHashCode(callSuper = true)
</#if>
public class ${table.name} <#if superClassSimpleName??>extends ${superClassSimpleName} </#if>implements Serializable<#if hasInterfaceList??>, ${interfaceSimpleNameListString}</#if> {
    
    private static final long serialVersionUID = 1L;

    <#list table.columns as column>
    /**
     * ${column.comment}
     */
    <#if column.primaryKey>
    @TableId(value = "${column.dbName}", type = IdType.AUTO)
    private ${column.type} ${column.name};
    <#else>
    @TableField(value = "${column.dbName}")
    private ${column.type} ${column.name};
    </#if>

    </#list>
}