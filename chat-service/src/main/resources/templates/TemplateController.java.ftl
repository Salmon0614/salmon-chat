<#assign firstChar = entity?substring(0, 1)?lower_case>
<#assign restChars = entity?substring(1)>
<#assign entityObj = firstChar + restChars>
package ${package.Controller};

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${packageName}.common.*;
import ${packageName}.exception.ThrowUtils;
import ${packageName}.model.dto.${entityObj}.${entity}AddRequest;
import ${packageName}.model.dto.${entityObj}.${entity}QueryRequest;
import ${packageName}.model.dto.${entityObj}.${entity}UpdateRequest;
import ${package.Entity}.${entity};
import ${packageName}.model.vo.${entityObj}.${entity}VO;
import ${packageName}.service.${entity}Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
<#if swagger>
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>
import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * <p>
 * ${table.comment!} 前端控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@RestController
@RequestMapping("/${entityObj}")
@Slf4j
<#if swagger>
@Tag(name = "${entity}Controller", description = "${table.comment!}前端控制器")
</#if>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>

    @Resource
    private ${entity}Service ${entityObj}Service;

    @Operation(summary = "添加${table.comment!}")
    @PostMapping("/add")
    public BaseResponse<Object> add${entity}(@RequestBody @Valid ${entity}AddRequest request) {
        ${entity} ${entityObj} = new ${entity}();
        BeanUtils.copyProperties(request, ${entityObj});
        ThrowUtils.throwIf(!${entityObj}Service.save(${entityObj}), ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(${entityObj}.getId());
    }

    @Operation(summary = "修改${table.comment!}")
    @PostMapping("/update")
    public BaseResponse<Object> update${entity}(@RequestBody @Valid ${entity}UpdateRequest request) {
        ${entity} ${entityObj} = ${entityObj}Service.getById(request.getId());
        ThrowUtils.throwIf(${entityObj} == null, ErrorCode.NOT_FOUND_ERROR);
        BeanUtils.copyProperties(request, ${entityObj});
        ThrowUtils.throwIf(!${entityObj}Service.updateById(${entityObj}), ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(${entityObj}.getId());
    }

    @Operation(summary = "删除${table.comment!}")
    @PostMapping("/delete")
    public BaseResponse<Object> delete${entity}(@RequestBody @Valid DeleteRequest request) {
        ThrowUtils.throwIf(!${entityObj}Service.removeById(request.getId()), ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(); 
    }

    @Operation(summary = "根据ID查询${table.comment!}")
    @PostMapping("/getById")
    public BaseResponse<${entity}VO> get${entity}ById(@RequestBody @Valid IdRequest request) {
        ${entity} ${entityObj} = ${entityObj}Service.getById(request.getId());
        ${entity}VO ${entityObj}VO = ${entity}VO.objToVo(${entityObj});
        return ResultUtils.success(${entityObj}VO);
    }

    @Operation(summary = "分页查询${table.comment!}")
    @PostMapping("/queryPage")
    public BaseResponse<Page<${entity}>> query${entity}Page(@RequestBody @Valid ${entity}QueryRequest request) {
        long current = request.getCurrent();
        long size = request.getPageSize();
        QueryWrapper<${entity}> queryWrapper = new QueryWrapper<>();
        // todo 查询条件
        Page<${entity}> page = ${entityObj}Service.page(new Page<>(current, size), queryWrapper);
        return ResultUtils.success(page);
    }
}
