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
 * 聊天消息
 * </p>
 *
 * @author Salmon
 * @since 2024-07-12
 */
@Data
@Accessors(chain = true)
@TableName("tb_chat_message")
@Schema(name = "ChatMessage", description = "聊天消息")
public class ChatMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "会话ID")
    @TableField("session_id")
    private String sessionId;

    @Schema(description = "消息类型")
    @TableField("message_type")
    private Integer messageType;

    @Schema(description = "消息内容")
    @TableField("message_content")
    private String messageContent;

    @Schema(description = "发送人账号")
    @TableField("send_user_account")
    private String sendUserAccount;

    @Schema(description = "发送人昵称")
    @TableField("send_user_nickname")
    private String sendUserNickname;

    @Schema(description = "发送时间")
    @TableField("send_time")
    private Long sendTime;

    @Schema(description = "接收联系人账号")
    @TableField("contact_account")
    private String contactAccount;

    @Schema(description = "联系人类型 0-用户 1-群聊")
    @TableField("contact_type")
    private Integer contactType;

    @Schema(description = "文件大小")
    @TableField("file_size")
    private Long fileSize;

    @Schema(description = "文件名称")
    @TableField("file_name")
    private String fileName;

    @Schema(description = "文件类型")
    @TableField("file_type")
    private Integer fileType;

    @Schema(description = "状态 0-正在发送 1-已发送")
    @TableField("status")
    private Integer status;

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
