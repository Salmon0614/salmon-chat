package com.salmon.chatService.model.vo.chatSessionUser;

import lombok.Data;
import com.salmon.chatService.model.po.ChatSessionUser;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.BeanUtils;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 聊天会话用户视图
 * </p>
 *
 * @author Salmon
 * @since 2024-07-12
 */
@Data
@Schema(name = "ChatSessionUserVO", description = "聊天会话用户视图")
public class ChatSessionUserVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Integer id;

    @Schema(description = "用户账号")
    private String userAccount;

    @Schema(description = "联系人账号")
    private String contactAccount;

    @Schema(description = "会话ID")
    private String sessionId;

    @Schema(description = "联系人名称")
    private String contactName;

    @Schema(description = "版本号")
    private Integer version;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "修改时间")
    private LocalDateTime updateTime;


    /**
     * 封装类转对象
     *
     * @param chatSessionUserVO 视图对象
     * @return 数据库对象
     */
    public static ChatSessionUser voToObj(ChatSessionUserVO chatSessionUserVO) {
        if (chatSessionUserVO == null) {
            return null;
        }
        ChatSessionUser chatSessionUser = new ChatSessionUser();
        BeanUtils.copyProperties(chatSessionUserVO, chatSessionUser);
        return chatSessionUser;
    }

    /**
     * 对象转封装类
     *
     * @param chatSessionUser 数据库对象
     * @return 视图对象
     */
    public static ChatSessionUserVO objToVo(ChatSessionUser chatSessionUser) {
        if (chatSessionUser == null) {
            return null;
        }
        ChatSessionUserVO chatSessionUserVO = new ChatSessionUserVO();
        BeanUtils.copyProperties(chatSessionUser, chatSessionUserVO);
        return chatSessionUserVO;
    }
}
