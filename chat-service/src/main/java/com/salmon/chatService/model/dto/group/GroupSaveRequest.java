package com.salmon.chatService.model.dto.group;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 用户保存群组请求
 * </p>
 *
 * @author Salmon
 * @since 2024-06-08
 */
@Data
@Schema(name = "GroupSaveRequest", description = "用户保存群组请求")
public class GroupSaveRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @Min(value = 1)
    private Integer id;

    @Schema(description = "群名")
    @NotBlank(message = "群名不能为空")
    private String groupName;

    @Schema(description = "群公告")
    @NotBlank(message = "群公告不能为空")
    private String groupNotice;

    @Schema(description = "加入类型 0-直接加入 1-管理员同意加入")
    @NotNull(message = "请选择加入类型")
    @Min(value = 0)
    @Max(value = 1)
    private Integer joinType;

    @Schema(description = "群封面")
    @TableField("group_cover")
    private String groupCover;
}