package ${packageName}.controller;
<#assign service="${table.name?uncap_first}Service">

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ${packageName}.service.${table.name}Service;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;

/**
 * ${table.name}Controller
<#if settings.author??>
 *
 * @author ${settings.author} on ${settings.now}
</#if>
 *
 */
@Slf4j
@CrossOrigin
@RestController()
@RequestMapping("/${table.name?uncap_first}")
public class ${table.name}Controller {

    @Autowired
    private ${table.name}Service service;

    <#if primaryKeyType??>
    @GetMapping("/{${primaryKey}}")
    public Object get${table.name}By${primaryKey?cap_first}(@PathVariable(name = "${primaryKey}") ${primaryKeyType} ${primaryKey}, HttpServletRequest request, HttpServletResponse response) {
        return service.get${table.name}By${primaryKey?cap_first}(${primaryKey});
    }
    </#if>

}