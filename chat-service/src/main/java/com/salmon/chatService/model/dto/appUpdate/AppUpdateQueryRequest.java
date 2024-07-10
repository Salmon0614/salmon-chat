package com.salmon.chatService.model.dto.appUpdate;

import com.salmon.chatService.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
/**
 * <p>
 * 查询APP发布请求
 * </p>
 *
 * @author Salmon
 * @since 2024-07-10
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "AppUpdateQueryRequest", description = "查询APP发布请求")
public class AppUpdateQueryRequest extends PageRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "创建时间开始")
    private LocalDateTime createTimeBegin;

    @Schema(description = "创建时间结束")
    private LocalDateTime createTimeEnd;

}