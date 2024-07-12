package com.salmon.chatService.model.vo.chatMessage;

import lombok.Data;
import com.salmon.chatService.model.po.ChatMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.BeanUtils;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 聊天消息视图
 * </p>
 *
 * @author Salmon
 * @since 2024-07-12
 */
@Data
@Schema(name = "ChatMessageVO", description = "聊天消息视图")
public class ChatMessageVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "会话ID")
    private String sessionId;

    @Schema(description = "消息类型")
    private Boolean messageType;

    @Schema(description = "消息内容")
    private String messageContent;

    @Schema(description = "发送人账号")
    private String sendUserAccount;

    @Schema(description = "发送人昵称")
    private String sendUserNickname;

    @Schema(description = "发送时间")
    private LocalDateTime sendTime;

    @Schema(description = "接收联系人账号")
    private String contactAccount;

    @Schema(description = "联系人类型 0-用户 1-群聊")
    private Boolean contactType;

    @Schema(description = "文件大小")
    private Long fileSize;

    @Schema(description = "文件名称")
    private String fileName;

    @Schema(description = "文件类型")
    private Byte fileType;

    @Schema(description = "状态 0-正在发送 1-已发送")
    private Boolean status;

    @Schema(description = "版本号")
    private Integer version;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "修改时间")
    private LocalDateTime updateTime;


    /**
     * 封装类转对象
     *
     * @param chatMessageVO 视图对象
     * @return 数据库对象
     */
    public static ChatMessage voToObj(ChatMessageVO chatMessageVO) {
        if (chatMessageVO == null) {
            return null;
        }
        ChatMessage chatMessage = new ChatMessage();
        BeanUtils.copyProperties(chatMessageVO, chatMessage);
        return chatMessage;
    }

    /**
     * 对象转封装类
     *
     * @param chatMessage 数据库对象
     * @return 视图对象
     */
    public static ChatMessageVO objToVo(ChatMessage chatMessage) {
        if (chatMessage == null) {
            return null;
        }
        ChatMessageVO chatMessageVO = new ChatMessageVO();
        BeanUtils.copyProperties(chatMessage, chatMessageVO);
        return chatMessageVO;
    }
}
