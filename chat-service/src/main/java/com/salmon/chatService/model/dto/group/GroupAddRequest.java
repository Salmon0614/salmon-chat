package com.salmon.chatService.model.dto.group;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
/**
 * <p>
 * 创建群组请求
 * </p>
 *
 * @author Salmon
 * @since 2024-06-08
 */
@Data
@Schema(name = "GroupAddRequest", description = "创建群组请求")
public class GroupAddRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Integer id;

    @Schema(description = "群聊号")
    private String groupNumber;

    @Schema(description = "群名")
    private String groupName;

    @Schema(description = "群主ID")
    private Integer groupOwnerId;

    @Schema(description = "群主账号")
    private String groupOwnerAccount;

    @Schema(description = "群通知")
    private String groupNotice;

    @Schema(description = "版本号")
    private Integer version;

    @Schema(description = "加入类型 0-直接加入 1-管理员同意加入")
    private Integer joinType;

    @Schema(description = "状态 0-正常 1-解散")
    private Integer status;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "修改时间")
    private LocalDateTime updateTime;

}