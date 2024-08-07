package com.salmon.chatService.model.dto.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * APP系统配置信息
 *
 * @author Salmon
 * @since 2024-06-06
 */
@Data
@Schema(name = "SystemConfigRequest", description = "APP系统配置信息")
public class SystemConfigRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "最大的群组数")
    private Integer maxGroupCount = 5;

    @Schema(description = "群组最大人数")
    private Integer maxGroupNumberCount = 500;

    @Schema(description = "图片大小")
    private Integer maxImageSize = 2;

    @Schema(description = "视频大小")
    private Integer waxVideoSize = 5;

    @Schema(description = "文件大小")
    private Integer maxFileSize = 5;

    @Schema(description = "机器人头像")
    private String robotAvatar = "";

    @Schema(description = "机器人昵称")
    private String robotNickName = "SalmonChat";

    @Schema(description = "机器人欢迎语")
    private String robotWelcome = "欢迎使用SalmonChat";
}
