package com.salmon.chatService.model.dto.userBeauty;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
/**
 * <p>
 * 创建靓号请求
 * </p>
 *
 * @author Salmon
 * @since 2024-06-05
 */
@Data
@Schema(name = "UserBeautyAddRequest", description = "创建靓号请求")
public class UserBeautyAddRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "ID")
    private Integer id;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "手机号")
    private String mobile;

    @Schema(description = "账号")
    private String account;

    @Schema(description = "使用的用户ID")
    private Integer userId;

    @Schema(description = "状态 0-未使用 1-已使用")
    private Integer status;

    @Schema(description = "版本号")
    private Integer version;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "修改时间")
    private LocalDateTime updateTime;

}