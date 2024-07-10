package com.salmon.chatService.model.dto.appUpdate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 检测更新请求
 * </p>
 *
 * @author Salmon
 * @since 2024-07-10
 */
@Data
@Schema(name = "CheckVersionRequest", description = "检测更新请求")
public class CheckVersionRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "当前的APP版本号")
    @NotBlank(message = "当前的APP版本号不能为空")
    private String appVersion;
}
