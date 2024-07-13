package com.salmon.chatService.model.vo.chat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.io.Serial;
import java.io.Serializable;

/**
 * 消息发送
 *
 * @author Salmon
 * @since 2024-07-13
 */
@Data
@Schema(name = "ChatMessageVO", description = "消息发送")
public class MessageSendVO<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "消息ID")
    private Integer id;

    @Schema(description = "会话ID")
    private String sessionId;

    @Schema(description = "发送人账号")
    private String sendUserAccount;

    @Schema(description = "发送人昵称")
    private String sendUserNickname;

    @Schema(description = "接收联系人账号")
    private String contactAccount;

    @Schema(description = "接收联系人昵称")
    private String contactNickname;

    @Schema(description = "消息内容")
    private String messageContent;

    @Schema(description = "最后的消息")
    private String lastMessage;

    @Schema(description = "消息类型")
    private Integer messageType;

    @Schema(description = "发送时间")
    private Long sendTime;

    @Schema(description = "联系人类型 0-用户 1-群聊")
    private Integer contactType;

    @Schema(description = "扩展消息")
    private T extendData;

    @Schema(description = "消息状态 0-正在发送 1-已发送（对于文件是异步上传用状态处理）")
    private Integer status;

    @Schema(description = "文件大小")
    private Long fileSize;

    @Schema(description = "文件名称")
    private String fileName;

    @Schema(description = "文件类型")
    private Integer fileType;

    @Schema(description = "群成员数")
    private Integer memberCount;

    public String getLastMessage() {
        if (!StringUtils.hasText(lastMessage)) {
            return messageContent;
        }
        return lastMessage;
    }
}
