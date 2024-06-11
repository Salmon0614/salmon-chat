package com.salmon.chatService.model.vo.contact;

import com.salmon.chatService.constant.Settings;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 联系人列表视图
 *
 * @author Salmon
 * @since 2024-06-09
 */
@Data
@NoArgsConstructor
@Schema(name = "UserContactVO", description = "联系人列表视图")
public class UserContactVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "ID")
    private Integer id;

    @Schema(description = "用户ID")
    private Integer userId;

    @Schema(description = "用户Id/群组ID")
    private Integer contactId;

    @Schema(description = "联系人账户")
    private String contactAccount;

    @Schema(description = "联系人/群名称")
    private String contactName;

//    @Schema(description = "描述")
//    private String description;
//
//    @Schema(description = "手机号")
//    private String mobile;
//
//    @Schema(description = "电子邮件")
//    private String email;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "是否机器人")
    private Boolean isRobot;

//    @Schema(description = "性别")
//    private Integer gender;
//
//    @Schema(description = "地区")
//    private String area;
//
//    @Schema(description = "最后登录时间")
//    private LocalDateTime lastLoginTime;
//
//    @Schema(description = "最后离线时间")
//    private LocalDateTime lastOffTime;

    public Boolean getRobot() {
        return Settings.ROBOT_ACCOUNT.equals(this.contactAccount);
    }
}
