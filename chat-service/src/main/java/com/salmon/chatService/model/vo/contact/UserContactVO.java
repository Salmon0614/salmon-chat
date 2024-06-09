package com.salmon.chatService.model.vo.contact;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Schema(name = "UserContactVO", description = "联系人视图")
public class UserContactVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "用户Id/群组ID")
    private Long contactId;

    @Schema(description = "账户")
    private String account;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "手机号")
    private String mobile;

    @Schema(description = "电子邮件")
    private String email;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "性别")
    private Integer gender;

    @Schema(description = "地区")
    private String area;

    @Schema(description = "最后登录时间")
    private LocalDateTime lastLoginTime;

    @Schema(description = "最后离线时间")
    private LocalDateTime lastOffTime;
}
