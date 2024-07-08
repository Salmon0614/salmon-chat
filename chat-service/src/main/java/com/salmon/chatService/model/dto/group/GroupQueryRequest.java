package com.salmon.chatService.model.dto.group;

import com.salmon.chatService.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
/**
 * <p>
 * 查询群组请求
 * </p>
 *
 * @author Salmon
 * @since 2024-06-08
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "GroupQueryRequest", description = "查询群组请求")
public class GroupQueryRequest extends PageRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "群聊号")
    private String groupNumber;

    @Schema(description = "群名")
    private String groupName;

    @Schema(description = "群主账号")
    private String groupOwnerAccount;

}