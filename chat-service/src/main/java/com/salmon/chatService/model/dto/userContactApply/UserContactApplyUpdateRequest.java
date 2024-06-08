package com.salmon.chatService.model.dto.userContactApply;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 更新联系人申请请求
 * </p>
 *
 * @author Salmon
 * @since 2024-06-08
 */
@Data
@Schema(name = "UserContactApplyUpdateRequest", description = "更新联系人申请请求")
public class UserContactApplyUpdateRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Integer id;

    @Schema(description = "申请人ID")
    private Integer applyUserId;

    @Schema(description = "接收人ID")
    private Integer receiveUserId;

    @Schema(description = "联系人类型 0-好友 1-群组")
    private Boolean contactType;

    @Schema(description = "联系人/群组ID")
    private Integer contactId;

    @Schema(description = "最近申请时间")
    private LocalDateTime lastApplyTime;

    @Schema(description = "版本号")
    private Integer version;

    @Schema(description = "状态 0-待处理 1-已同意 2-已拒绝 3-已拉黑")
    private Boolean status;

    @Schema(description = "申请信息")
    private String applyInfo;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "修改时间")
    private LocalDateTime updateTime;

}