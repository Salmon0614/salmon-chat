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
 * APP发布
 * </p>
 *
 * @author Salmon
 * @since 2024-07-10
 */
@Data
@Accessors(chain = true)
@TableName("tb_app_update")
@Schema(name = "AppUpdate", description = "APP发布")
public class AppUpdate implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "APP版本号")
    @TableField("app_version")
    private String appVersion;

    @Schema(description = "更新描述")
    @TableField("update_desc")
    private String updateDesc;

    @Schema(description = "状态 0-未发布 1-灰度发布 2-全网发布")
    @TableField("status")
    private Integer status;

    @Schema(description = "版本号")
    @TableField("version")
    @Version
    private Integer version;

    @Schema(description = "文件类型 0-本地文件 1-外链")
    @TableField("file_type")
    private Integer fileType;

    @Schema(description = "外链地址")
    @TableField("outer_link")
    private String outerLink;

    @Schema(description = "参与灰度发布的用户ID")
    @TableField("grayscale_uid")
    private String grayscaleUid;

    @Schema(description = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "修改时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @Schema(description = "发布时间")
    @TableField(value = "post_time")
    private LocalDateTime postTime;
}
