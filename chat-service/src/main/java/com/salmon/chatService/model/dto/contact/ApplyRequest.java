package com.salmon.chatService.model.dto.contact;

import com.salmon.chatService.model.enums.contact.ApplyTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serial;
import java.io.Serializable;

/**
 * 申请加入好友/群聊请求
 *
 * @author Salmon
 * @since 2024-06-09
 */
@Data
@Schema(name = "ApplyRequest", description = "申请加入好友/群聊请求")
public class ApplyRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "搜索信息不能为空")
    @Schema(description = "用户账号/群聊号")
    private String contactAccount;

    @Schema(description = "申请信息")
    private String applyInfo;

    @Schema(description = "申请方式 0-搜索方式 1-群里添加方式")
    private Integer applyType = ApplyTypeEnum.SEARCH.getValue();

    @Schema(description = "群聊ID（申请方式为1时必传）")
    private Integer groupId;
}
