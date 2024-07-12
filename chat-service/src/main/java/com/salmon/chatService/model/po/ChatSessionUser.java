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
 * 聊天会话用户
 * </p>
 *
 * @author Salmon
 * @since 2024-07-12
 */
@Data
@Accessors(chain = true)
@TableName("tb_chat_session_user")
@Schema(name = "ChatSessionUser", description = "聊天会话用户")
    public class ChatSessionUser implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
            @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "用户账号")
        @TableField("user_account")
    private String userAccount;

    @Schema(description = "联系人账号")
        @TableField("contact_account")
    private String contactAccount;

    @Schema(description = "会话ID")
        @TableField("session_id")
    private String sessionId;

    @Schema(description = "联系人名称")
        @TableField("contact_name")
    private String contactName;

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
