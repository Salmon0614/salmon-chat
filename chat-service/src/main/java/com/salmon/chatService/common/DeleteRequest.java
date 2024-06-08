package com.salmon.chatService.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

/**
 * 删除请求
 *
 * @author Salmon
 * @since 2024-05-19
 */
@Data
@Schema(name = "DeleteRequest", description = "通用删除请求")
public class DeleteRequest implements Serializable {

    /**
     * 序列化版本
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @NotNull(message = "ID不能为空")
    @Schema(description = "ID")
    private Long id;
}
