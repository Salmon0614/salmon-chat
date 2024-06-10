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
    /**
     * 默认群封面
     */
    String DEFAULT_GROUP_COVER = "https://img1.baidu.com/it/u=4069997521,806474628&fm=253&fmt=auto&app=120&f=JPEG?w=800&h=800";
}
