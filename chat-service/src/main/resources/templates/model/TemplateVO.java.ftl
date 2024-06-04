<#assign firstChar = entity?substring(0, 1)?lower_case>
<#assign restChars = entity?substring(1)>
<#assign entityObj = firstChar + restChars>
package ${packageVo}.${entityObj};

import lombok.Data;
import ${package.Entity}.${entity};
<#if swagger>
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.BeanUtils;
</#if>
import java.io.Serial;
import java.io.Serializable;
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
 * ${table.comment!}视图
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Data
@Schema(name = "${entity}VO", description = "${table.comment!}视图")
public class ${entity}VO implements Serializable {
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
    private ${field.propertyType} ${field.propertyName};

</#list>
<#------------  END 字段循环遍历  ---------->

    /**
     * 封装类转对象
     *
     * @param ${entityObj}VO 视图对象
     * @return 数据库对象
     */
    public static ${entity} voToObj(${entity}VO ${entityObj}VO) {
        if (${entityObj}VO == null) {
            return null;
        }
        ${entity} ${entityObj} = new ${entity}();
        BeanUtils.copyProperties(${entityObj}VO, ${entityObj});
        return ${entityObj};
    }

    /**
     * 对象转封装类
     *
     * @param ${entityObj} 数据库对象
     * @return 视图对象
     */
    public static ${entity}VO objToVo(${entity} ${entityObj}) {
        if (${entityObj} == null) {
            return null;
        }
        ${entity}VO ${entityObj}VO = new ${entity}VO();
        BeanUtils.copyProperties(${entityObj}, ${entityObj}VO);
        return ${entityObj}VO;
    }
}
