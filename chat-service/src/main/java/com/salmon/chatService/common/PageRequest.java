package com.salmon.chatService.common;

import com.salmon.chatService.constant.CommonConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 分页请求
 *
 * @author Salmon
 * @since 2024-05-19
 */
@Data
@Schema(name = "PageRequest", description = "通用分页请求")
public class PageRequest {

    /**
     * 当前页号
     */
    @Schema(description = "当前页号")
    @Min(value = 1)
    private long current = 1;

    /**
     * 页面大小
     */
    @Schema(description = "页面大小")
    private long pageSize = 10;

    /**
     * 排序字段
     */
    @Schema(description = "排序字段")
    private String sortField;

    /**
     * 排序顺序（默认升序）
     */
    @Schema(description = "排序顺序（默认升序）")
    private String sortOrder = CommonConstant.SORT_ORDER_ASC;
}
