<#assign firstChar = entity?substring(0, 1)?lower_case>
<#assign restChars = entity?substring(1)>
<#assign entityObj = firstChar + restChars>
package ${packageDto}.${entityObj};

import lombok.Data;
<#if swagger>
import io.swagger.v3.oas.annotations.media.Schema;
</#if>
import java.io.Serial;
import java.io.Serializable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
<#assign a = 0>
<#assign b = 0>
<#assign c = 0>
<#list table.fields as field>
    <#if field.propertyType = "LocalDateTime" && a = 0>
import java.time.LocalDateTime;
        <#assign a = a + 1>
    </#if>
    <#if field.propertyType = "LocalDate" && b = 0>
import java.time.LocalDate;
        <#assign b = b + 1>
    </#if>
    <#if field.propertyType = "BigDecimal" && c = 0>
 import java.math.BigDecimal;
        <#assign c = c + 1>
    </#if>
</#list>

/**
 * <p>
 * 更新${table.comment!}请求
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Data
@Schema(name = "${entity}UpdateRequest", description = "更新${table.comment!}请求")
public class ${entity}UpdateRequest implements Serializable {
<#if entitySerialVersionUID>
    @Serial
    private static final long serialVersionUID = 1L;
</#if>

<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
    <#if field.comment!?length gt 0>
        <#if swagger>
    @Schema(description = "${field.comment}")
        <#else>
    /**
     * ${field.comment}
     */
        </#if>
    </#if>
    <#if field.propertyType = "String">
    @NotBlank(message = "${field.comment}不能为空")
    <#else>
    @NotNull(message = "${field.comment}不能为空")
    </#if>
    <#if field.keyFlag>
    @Min(value = 1)
    </#if>
    private ${field.propertyType} ${field.propertyName};

</#list>
<#------------  END 字段循环遍历  ---------->
}