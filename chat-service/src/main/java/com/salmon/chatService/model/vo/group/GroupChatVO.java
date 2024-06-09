package com.salmon.chatService.model.vo.group;

import com.salmon.chatService.model.vo.contact.UserContactVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 群聊详情（群信息+群成员）
 *
 * @author Salmon
 * @since 2024-06-09
 */
@Data
@Schema(name = "GroupChatVO", description = "群聊详情（群信息+群成员）")
public class GroupChatVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "群详情信息")
    private GroupVO groupVO;

    @Schema(description = "群成员")
    private List<UserContactVO> userContactVOS;
}
