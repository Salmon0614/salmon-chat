package com.salmon.chatService.model.dto.contact;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 加载联系人请求
 *
 * @author Salmon
 * @since 2024-06-10
 */
@Data
@Schema(name = "LoadContactRequest", description = "加载联系人请求")
public class LoadContactRequest {

    @Schema(description = "联系人类型 0-好友 1-群聊")
    @NotNull(message = "联系人类型不能为空")
    @Min(value = 0)
    @Max(value = 1)
    private Integer contactType;
}
