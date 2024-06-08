package com.salmon.chatService.model.dto.userContact;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 更新联系人请求
 * </p>
 *
 * @author Salmon
 * @since 2024-06-08
 */
@Data
@Schema(name = "UserContactUpdateRequest", description = "更新联系人请求")
public class UserContactUpdateRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Integer id;

    @Schema(description = "用户ID")
    private Integer userId;

    @Schema(description = "联系人ID或者群组ID")
    private Integer contactId;

    @Schema(description = "联系人类型 0-好友 1-群组")
    private Integer contactType;

    @Schema(description = "版本号")
    private Integer version;

    @Schema(description = "状态 0-非好友 1-好友 2-已删除好友 3-被好友删除 4-拉黑好友 5-被好友拉黑")
    private Integer status;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "修改时间")
    private LocalDateTime updateTime;

}