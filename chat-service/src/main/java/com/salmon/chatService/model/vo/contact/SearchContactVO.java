package com.salmon.chatService.model.vo.contact;

import com.salmon.chatService.model.enums.contact.UserContactTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

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

    @Schema(description = "联系人类型描述")
    private String contactTypeDesc;

    @Schema(description = "来源类型 0-搜索账号 1-通过群聊添加 2-搜索邮箱 3-搜索手机号")
    private Integer originType;

    @Schema(description = "昵称/群名称")
    private String name;

    @Schema(description = "状态")
    private Integer status;

    @Schema(description = "性别")
    private Integer gender;

    @Schema(description = "区域")
    private String area;

    @Schema(description = "群封面/用户头像")
    private String avatar;

    public String getContactTypeDesc() {
        UserContactTypeEnum contactTypeEnum = UserContactTypeEnum.getEnumByValue(this.contactType);
        if (Objects.isNull(contactTypeEnum)) {
            return "";
        }
        return contactTypeEnum.getDesc();
    }
}
