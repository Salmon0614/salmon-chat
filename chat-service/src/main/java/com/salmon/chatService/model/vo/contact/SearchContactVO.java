package com.salmon.chatService.model.vo.contact;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 搜索结果视图
 *
 * @author Salmon
 * @since 2024-06-09
 */
@Data
@NoArgsConstructor
@Schema(name = "SearchContactVO", description = "搜索结果视图")
public class SearchContactVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户/群聊ID")
    private Integer id;

    @Schema(description = "用户账号/群聊号")
    private String account;

    @Schema(description = "联系人类型")
    private Integer contactType;

    @Schema(description = "昵称/群名称")
    private String name;

    @Schema(description = "状态")
    private Integer status;

    @Schema(description = "性别")
    private String gender;

    @Schema(description = "区域")
    private String area;

    @Schema(description = "群封面/用户头像")
    private String avatar;
}
