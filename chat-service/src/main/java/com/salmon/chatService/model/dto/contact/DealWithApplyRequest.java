package com.salmon.chatService.model.dto.contact;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

/**
 * 处理申请请求
 *
 * @author Salmon
 * @since 2024-06-10
 */
@Data
@Schema(name = "DealWithApplyRequest", description = "处理申请请求")
public class DealWithApplyRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "申请ID")
    @NotNull(message = "申请ID不能为空")
    @Min(value = 1)
    private Integer applyId;

    @Schema(description = "状态 1-已同意 2-已拒绝 3-已拉黑")
    @NotNull(message = "状态不能为空")
    @Min(value = 1)
    @Max(value = 3)
    private Integer status;
}
