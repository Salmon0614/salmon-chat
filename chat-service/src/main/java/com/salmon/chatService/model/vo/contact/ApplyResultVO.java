package com.salmon.chatService.model.vo.contact;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 申请加入结果
 *
 * @author Salmon
 * @since 2024-06-09
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "ApplyResultVO", description = "申请加入结果")
public class ApplyResultVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "加入类型 0-直接加入 1-好友/管理员同意加入")
    private Integer joinType;
}
