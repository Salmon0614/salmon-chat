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

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
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
    private Boolean gender;

    @Schema(description = "密码")
    @TableField("password")
    private String password;

    @Schema(description = "盐")
    @TableField("secret")
    private String secret;

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
    private Boolean status;

    @Schema(description = "版本号")
    @TableField("version")
    @Version
    private Integer version;

    @Schema(description = "加入好友类型 0-直接加入 1-需要验证")
    @TableField("join_type")
    private Boolean joinType;

    @Schema(description = "最近登录时间")
    @TableField("last_login_time")
    private LocalDateTime lastLoginTime;

    @Schema(description = "离线时间")
    @TableField("last_off_time")
    private LocalDateTime lastOffTime;

    @Schema(description = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "修改时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @Schema(description = "是否删除 0-未删除 1-已删除")
    @TableField("is_delete")
    private Boolean isDelete;


}
