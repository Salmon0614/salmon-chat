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
 * 群组
 * </p>
 *
 * @author Salmon
 * @since 2024-06-08
 */
@Data
@Accessors(chain = true)
@TableName("tb_group")
@Schema(name = "Group", description = "群组")
public class Group implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "群号")
    @TableField("group_number")
    private String groupNumber;

    @Schema(description = "群名")
    @TableField("group_name")
    private String groupName;

    @Schema(description = "群主ID")
    @TableField("group_owner_id")
    private Integer groupOwnerId;

    @Schema(description = "群主账号")
    @TableField("group_owner_account")
    private String groupOwnerAccount;

    @Schema(description = "群通知")
    @TableField("group_notice")
    private String groupNotice;

    @Schema(description = "版本号")
    @TableField("version")
    @Version
    private Integer version;

    @Schema(description = "加入类型 0-直接加入 1-管理员同意加入")
    @TableField("join_type")
    private Boolean joinType;

    @Schema(description = "状态 0-正常 1-解散")
    @TableField("status")
    private Boolean status;

    @Schema(description = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "修改时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


}
