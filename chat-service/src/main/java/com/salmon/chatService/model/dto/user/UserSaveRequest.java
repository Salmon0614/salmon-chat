package com.salmon.chatService.model.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

/**
 * @author Salmon
 * @since 2024-06-10
 */
@Data
@Schema(name = "UserAddRequest", description = "创建用户请求")
public class UserSaveRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "昵称")
    @NotBlank(message = "昵称不能为空")
    private String nickname;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "头像")
    @NotBlank(message = "请上传头像")
    private String avatar;

    @Schema(description = "性别 0-女 1-男")
    @NotNull(message = "请选择性别")
    private Integer gender;

    @Schema(description = "加入好友类型 0-直接加入 1-需要验证")
    @NotNull(message = "请选择加入好友类型")
    private Integer joinType;

    @Schema(description = "地区")
    private String area;

    @Schema(description = "地区编号")
    private String areaCode;
}
