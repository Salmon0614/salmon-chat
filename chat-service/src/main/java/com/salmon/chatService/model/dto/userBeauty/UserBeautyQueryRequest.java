package com.salmon.chatService.model.dto.userBeauty;

import com.salmon.chatService.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
/**
 * <p>
 * 查询靓号请求
 * </p>
 *
 * @author Salmon
 * @since 2024-06-05
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "UserBeautyQueryRequest", description = "查询靓号请求")
public class UserBeautyQueryRequest extends PageRequest implements Serializable {
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
    private Boolean status;

    @Schema(description = "版本号")
    private Integer version;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "修改时间")
    private LocalDateTime updateTime;

}