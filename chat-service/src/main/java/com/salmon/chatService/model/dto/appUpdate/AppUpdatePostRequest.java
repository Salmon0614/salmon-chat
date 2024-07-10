package com.salmon.chatService.model.dto.appUpdate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 发布APP请求
 * </p>
 *
 * @author Salmon
 * @since 2024-07-10
 */
@Data
@Schema(name = "AppUpdatePostRequest", description = "发布APP请求")
public class AppUpdatePostRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "ID")
    @NotNull(message = "ID不能为空")
    @Min(value = 1)
    private Integer id;

    @Schema(description = "状态 0-未发布 1-灰度发布 2-全网发布")
    @NotNull(message = "状态不能为空")
    @Min(value = 0)
    @Max(value = 2)
    private Integer status;

    @Schema(description = "参与灰度发布的用户ID")
    @NotBlank(message = "参与灰度发布的用户ID不能为空")
    private String grayscaleUid;
}
