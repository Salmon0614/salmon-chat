package com.salmon.chatService.model.dto.user;

import com.salmon.chatService.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 查询用户请求
 * </p>
 *
 * @author Salmon
 * @since 2024-06-04
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "UserQueryRequest", description = "查询用户请求")
public class UserQueryRequest extends PageRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Integer id;

    @Schema(description = "账号")
    private String account;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "手机号")
    private String mobile;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "性别 0-女 1-男")
    private Boolean gender;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "盐")
    private String salt;

    @Schema(description = "地区")
    private String area;

    @Schema(description = "地区编号")
    private String areaCode;

    @Schema(description = "收货地址")
    private String address;

    @Schema(description = "状态 0-禁用 1-启用")
    private Integer status;

    @Schema(description = "加入好友类型 0-直接加入 1-需要验证")
    private Integer joinType;

    @Schema(description = "最近登录时间")
    private LocalDateTime lastLoginTime;

    @Schema(description = "离线时间")
    private LocalDateTime lastOffTime;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}