package com.salmon.chatService.model.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.io.Serial;
import java.util.Objects;

import com.salmon.chatService.common.StatusEnum;
import com.salmon.chatService.model.enums.user.UserGenderEnum;
import com.salmon.chatService.model.enums.user.UserJoinTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author Salmon
 * @since 2024-06-04
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("tb_user")
@Schema(name = "User", description = "用户")
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "账号")
    @TableField("account")
    private String account;

    @Schema(description = "昵称")
    @TableField("nickname")
    private String nickname;

    @Schema(description = "描述")
    @TableField("description")
    private String description;

    @Schema(description = "手机号")
    @TableField("mobile")
    private String mobile;

    @Schema(description = "邮箱")
    @TableField("email")
    private String email;

    @Schema(description = "头像")
    @TableField("avatar")
    private String avatar;

    @Schema(description = "性别 0-女 1-男")
    @TableField("gender")
    private Integer gender;

    @Schema(description = "性别描述")
    @TableField(exist = false)
    private String genderDesc;

    @Schema(description = "密码")
    @TableField("password")
    private String password;

    @Schema(description = "盐")
    @TableField("salt")
    private String salt;

    @Schema(description = "地区")
    @TableField("area")
    private String area;

    @Schema(description = "地区编号")
    @TableField("area_code")
    private String areaCode;

    @Schema(description = "收货地址")
    @TableField("address")
    private String address;

    @Schema(description = "状态 0-禁用 1-启用")
    @TableField("status")
    private Integer status;

    @Schema(description = "状态描述")
    @TableField(exist = false)
    private String statusDesc;

    @Schema(description = "角色 0-普通用户 1-管理员")
    @TableField("role")
    private Integer role;

    @Schema(description = "版本号")
    @TableField("version")
    @Version
    private Integer version;

    @Schema(description = "加入好友类型 0-直接加入 1-需要验证")
    @TableField("join_type")
    private Integer joinType;

    @Schema(description = "加入好友类型描述")
    @TableField(exist = false)
    private String joinTypeDesc;

    @Schema(description = "最近登录时间")
    @TableField("last_login_time")
    private LocalDateTime lastLoginTime;

    @Schema(description = "离线时间（毫秒）")
    @TableField("last_off_time")
    private Long lastOffTime;

    @Schema(description = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "修改时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @Schema(description = "是否删除 0-未删除 1-已删除")
    @TableField("is_delete")
    private Boolean isDelete;

    public String getStatusDesc() {
        StatusEnum statusEnum = StatusEnum.getEnumByValue(this.status);
        if (Objects.isNull(statusEnum)) {
            return "";
        }
        return statusEnum.getDesc();
    }

    public String getJoinTypeDesc() {
        UserJoinTypeEnum joinTypeEnum = UserJoinTypeEnum.getEnumByValue(this.joinType);
        if (Objects.isNull(joinTypeEnum)) {
            return "";
        }
        return joinTypeEnum.getDesc();
    }

    public String getGenderDesc() {
        UserGenderEnum genderEnum = UserGenderEnum.getEnumByValue(this.gender);
        if (Objects.isNull(genderEnum)) {
            return "";
        }
        return genderEnum.getDesc();
    }
}
