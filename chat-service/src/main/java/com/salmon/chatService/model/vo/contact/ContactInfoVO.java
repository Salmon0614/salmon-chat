package com.salmon.chatService.model.vo.contact;

import com.salmon.chatService.model.enums.userContact.UserContactStatusEnum;
import com.salmon.chatService.model.po.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * 联系人详情视图
 *
 * @author Salmon
 * @since 2024-06-10
 */
@Data
@NoArgsConstructor
@Schema(name = "ContactInfoVO", description = "联系人详情视图")
public class ContactInfoVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户ID")
    private Integer id;

    @Schema(description = "账户")
    private String account;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "手机号")
    private String mobile;

    @Schema(description = "电子邮件")
    private String email;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "性别")
    private Integer gender;

    @Schema(description = "性别描述")
    private String genderDesc;

    @Schema(description = "地区")
    private String area;

    @Schema(description = "状态")
    private Integer contactStatus;

    @Schema(description = "状态描述")
    private String contactStatusDesc;

    public String getContactStatusDesc() {
        UserContactStatusEnum statusEnum = UserContactStatusEnum.getEnumByValue(this.contactStatus);
        if (Objects.isNull(statusEnum)) {
            return "";
        }
        return statusEnum.getDesc();
    }

    /**
     * 对象转封装类
     *
     * @param user 数据库对象
     * @return 视图对象
     */
    public static ContactInfoVO objToVo(User user) {
        if (user == null) {
            return null;
        }
        ContactInfoVO contactInfoVO = new ContactInfoVO();
        BeanUtils.copyProperties(user, contactInfoVO);
        return contactInfoVO;
    }
}
