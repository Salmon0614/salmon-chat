package com.salmon.chatService.model.dto.userContactApply;

import com.salmon.chatService.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
/**
 * <p>
 * 查询联系人申请请求
 * </p>
 *
 * @author Salmon
 * @since 2024-06-08
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "UserContactApplyQueryRequest", description = "查询联系人申请请求")
public class UserContactApplyQueryRequest extends PageRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Schema(description = "id")
    private Integer id;

    @Schema(description = "申请人ID")
    private Integer applyUserId;

    @Schema(description = "接收人ID")
    private Integer receiveUserId;

    @Schema(description = "联系人类型 0-好友 1-群组")
    private Integer contactType;

    @Schema(description = "联系人/群组ID")
    private Integer contactId;

    @Schema(description = "最近申请时间")
    private LocalDateTime lastApplyTime;

    @Schema(description = "版本号")
    private Integer version;

    @Schema(description = "状态 0-待处理 1-已同意 2-已拒绝 3-已拉黑")
    private Integer status;

    @Schema(description = "申请信息")
    private String applyInfo;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "修改时间")
    private LocalDateTime updateTime;

}