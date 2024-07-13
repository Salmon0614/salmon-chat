package com.salmon.chatService.model.vo.app;

import com.salmon.chatService.constant.Settings;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 系统配置参数
 *
 * @author Salmon
 * @since 2024-06-05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SystemConfigVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    // 最大的群组数
    private Integer maxGroupCount = 5;
    // 群组最大人数
    private Integer maxGroupNumberCount = 500;
    // 图片大小
    private Integer maxImageSize = 2;
    // 视频大小
    private Integer waxVideoSize = 5;
    // 文件大小
    private Integer maxFileSize = 5;
    // 机器人头像
    private String robotAvatar = "";
    // 机器人ID
    private Integer robotUid = Settings.ROBOT_ID;
    // 机器人账号
    private String robotUAccount = Settings.ROBOT_ACCOUNT;
    // 机器人昵称
    private String robotNickName = "SalmonChat";
    // 机器人欢迎语
    private String robotWelcome = "欢迎使用SalmonChat";
}
