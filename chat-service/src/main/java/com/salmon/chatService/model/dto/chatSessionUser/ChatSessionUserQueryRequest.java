package com.salmon.chatService.model.dto.chatSessionUser;

import com.salmon.chatService.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
/**
 * <p>
 * 查询聊天会话用户请求
 * </p>
 *
 * @author Salmon
 * @since 2024-07-12
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "ChatSessionUserQueryRequest", description = "查询聊天会话用户请求")
public class ChatSessionUserQueryRequest extends PageRequest implements Serializable {
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

}