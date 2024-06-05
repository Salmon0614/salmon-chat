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
 * 靓号
 * </p>
 *
 * @author Salmon
 * @since 2024-06-05
 */
@Data
@Accessors(chain = true)
@TableName("tb_user_beauty")
@Schema(name = "UserBeauty", description = "靓号")
public class UserBeauty implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "邮箱")
    @TableField("email")
    private String email;

    @Schema(description = "手机号")
    @TableField("mobile")
    private String mobile;

    @Schema(description = "账号")
    @TableField("account")
    private String account;

    @Schema(description = "使用的用户ID")
    @TableField("user_id")
    private Integer userId;

    @Schema(description = "状态 0-未使用 1-已使用")
    @TableField("status")
    private Boolean status;

    @Schema(description = "版本号")
    @TableField("version")
    @Version
    private Integer version;

    @Schema(description = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "修改时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}
