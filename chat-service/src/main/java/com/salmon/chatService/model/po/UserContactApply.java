package com.salmon.chatService.model.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.io.Serial;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 联系人申请
 * </p>
 *
 * @author Salmon
 * @since 2024-06-08
 */
@Data
@Accessors(chain = true)
@TableName("tb_user_contact_apply")
@Schema(name = "UserContactApply", description = "联系人申请")
public class UserContactApply implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "申请人ID")
    @TableField("apply_user_id")
    private Integer applyUserId;

    @Schema(description = "接收人ID")
    @TableField("receive_user_id")
    private Integer receiveUserId;

    @Schema(description = "联系人类型 0-好友 1-群组")
    @TableField("contact_type")
    private Integer contactType;

    @Schema(description = "联系人/群组ID")
    @TableField("contact_id")
    private Integer contactId;

    @Schema(description = "最近申请时间")
    @TableField("last_apply_time")
    private LocalDateTime lastApplyTime;

    @Schema(description = "版本号")
    @TableField("version")
    @Version
    private Integer version;

    @Schema(description = "状态 0-待处理 1-已同意 2-已拒绝 3-已拉黑")
    @TableField("status")
    private Integer status;

    @Schema(description = "申请信息")
    @TableField("apply_info")
    private String applyInfo;

    @Schema(description = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "修改时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


}
