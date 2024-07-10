package com.salmon.chatService.model.dto.appUpdate;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;
import java.io.Serializable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 更新APP发布请求
 * </p>
 *
 * @author Salmon
 * @since 2024-07-10
 */
@Data
@Schema(name = "AppUpdateUpdateRequest", description = "更新APP发布请求")
public class AppUpdateUpdateRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "ID")
    @NotNull(message = "ID不能为空")
    @Min(value = 1)
    private Integer id;

    @Schema(description = "APP版本号")
    @NotBlank(message = "APP版本号不能为空")
    private String appVersion;

    @Schema(description = "更新描述")
    @NotBlank(message = "更新描述不能为空")
    private String updateDesc;

    @Schema(description = "文件类型 0-本地文件 1-外链")
    @NotNull(message = "文件类型不能为空")
    @Min(value = 0)
    @Max(value = 1)
    private Integer fileType;

    @Schema(description = "外链地址")
    @NotBlank(message = "外链地址不能为空")
    private String outerLink;

}