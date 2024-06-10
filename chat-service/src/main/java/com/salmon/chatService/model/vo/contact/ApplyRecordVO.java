package com.salmon.chatService.model.vo.contact;

import com.salmon.chatService.model.enums.contact.ContactApplyStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 申请记录视图
 *
 * @author Salmon
 * @since 2024-06-10
 */
@Data
@Schema(name = "ApplyRecordVO", description = "申请记录视图")
public class ApplyRecordVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Integer id;

    @Schema(description = "申请人ID")
    private Integer applyUserId;

    @Schema(description = "申请人昵称/群聊名称")
    private String contactName;

    @Schema(description = "联系人/群组ID")
    private Integer contactId;

    @Schema(description = "最近申请时间")
    private LocalDateTime lastApplyTime;

    @Schema(description = "状态 0-待处理 1-已同意 2-已拒绝 3-已拉黑")
    private Integer status;

    @Schema(description = "状态 0-待处理 1-已同意 2-已拒绝 3-已拉黑")
    private String statusDesc;

    @Schema(description = "申请信息")
    private String applyInfo;

    @Schema(description = "来源类型 0-搜索账号 1-通过群聊添加 2-搜索邮箱 3-搜索手机号")
    private Integer originType;

    @Schema(description = "联系人类型 0-好友 1-群聊")
    private Integer contactType;

    @Schema(description = "用户头像/群封面")
    private String avatar;

    @Schema(description = "性别")
    private Integer gender;

    @Schema(description = "地区")
    private String area;

    public String getStatusDesc() {
        ContactApplyStatusEnum applyStatusEnum = ContactApplyStatusEnum.getEnumByValue(this.status);
        if (Objects.isNull(applyStatusEnum)) {
            return "";
        }
        return applyStatusEnum.getDesc();
    }
}
