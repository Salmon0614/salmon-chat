package com.salmon.chatService.constant;

/**
 * 设置
 *
 * @author Salmon
 * @since 2024-06-08
 */
public interface Settings {
    String APP_NAME = "SalmonChat";

    /**
     * 登录信息保存7天
     */
    long SESSION_EXPIRE_TIME = 60 * 60 * 24 * 7;

    /**
     * 机器人账号
     */
    String ROBOT_ACCOUNT = "SU9913111088";
}
