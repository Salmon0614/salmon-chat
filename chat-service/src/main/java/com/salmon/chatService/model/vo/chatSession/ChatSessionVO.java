package com.salmon.chatService.model.vo.chatSession;

import lombok.Data;
import com.salmon.chatService.model.po.ChatSession;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.BeanUtils;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 聊天会话信息视图
 * </p>
 *
 * @author Salmon
 * @since 2024-07-12
 */
@Data
@Schema(name = "ChatSessionVO", description = "聊天会话信息视图")
public class ChatSessionVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "ID")
    private Integer id;

    @Schema(description = "会话ID")
    private String sessionId;

    @Schema(description = "最后收到的消息")
    private String lastMessage;

    @Schema(description = "最近接收消息时间（毫秒）")
    private Long lastReceiveTime;

    @Schema(description = "版本号")
    private Integer version;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;


    /**
     * 封装类转对象
     *
     * @param chatSessionVO 视图对象
     * @return 数据库对象
     */
    public static ChatSession voToObj(ChatSessionVO chatSessionVO) {
        if (chatSessionVO == null) {
            return null;
        }
        ChatSession chatSession = new ChatSession();
        BeanUtils.copyProperties(chatSessionVO, chatSession);
        return chatSession;
    }

    /**
     * 对象转封装类
     *
     * @param chatSession 数据库对象
     * @return 视图对象
     */
    public static ChatSessionVO objToVo(ChatSession chatSession) {
        if (chatSession == null) {
            return null;
        }
        ChatSessionVO chatSessionVO = new ChatSessionVO();
        BeanUtils.copyProperties(chatSession, chatSessionVO);
        return chatSessionVO;
    }
}
