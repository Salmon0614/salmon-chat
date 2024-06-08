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
 * 联系人
 * </p>
 *
 * @author Salmon
 * @since 2024-06-08
 */
@Data
@Accessors(chain = true)
@TableName("tb_user_contact")
@Schema(name = "UserContact", description = "联系人")
    public class UserContact implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
            @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "用户ID")
        @TableField("user_id")
    private Integer userId;

    @Schema(description = "联系人ID或者群组ID")
        @TableField("contact_id")
    private Integer contactId;

    @Schema(description = "联系人类型 0-好友 1-群组")
        @TableField("contact_type")
    private Boolean contactType;

    @Schema(description = "版本号")
        @TableField("version")
        @Version
    private Integer version;

    @Schema(description = "状态 0-非好友 1-好友 2-已删除好友 3-被好友删除 4-拉黑好友 5-被好友拉黑")
        @TableField("status")
    private Boolean status;

    @Schema(description = "创建时间")
            @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "修改时间")
            @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


}
