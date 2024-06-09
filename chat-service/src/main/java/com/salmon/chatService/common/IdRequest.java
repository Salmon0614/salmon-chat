package com.salmon.chatService.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

/**
 * 通用ID请求
 *
 * @author Salmon
 * @since 2024-05-19
 */
@Data
@Schema(name = "IdRequest", description = "通用ID请求")
public class IdRequest implements Serializable {

    /**
     * 序列化版本
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @NotNull(message = "ID不能为空")
    @Min(value = 1)
    @Schema(description = "ID")
    private Long id;
}
