package com.salmon.chatService.model.dto.contact;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serial;
import java.io.Serializable;

/**
 * 搜索联系人请求
 *
 * @author Salmon
 * @since 2024-06-09
 */
@Data
@Schema(name = "SearchRequest)", description = "搜索联系人请求")
public class SearchRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "搜索信息不能为空")
    @Schema(description = "用户账号/群聊号")
    private String contactAccount;
}
