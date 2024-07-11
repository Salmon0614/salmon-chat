package com.salmon.chatService.constant;

/**
 * Redis key 常量
 *
 * @author Salmon
 * @since 2024-06-05
 */
public interface RedisPrefixConstant {
    String APP = "salmon_chat:";
    // 验证码
    String CHECK_CODE = APP + "account:check_code:";
    // 10分钟
    Long CODE_EXPIRE_TIME = 10 * 60L;
    // 限流
    String ACCESS_LIMIT = APP + "access_limit:";
    // APP 配置信息
    String APP_SYS_CONFIG_SETTING = APP + "app_sys_config_setting";

    //----------------------登录-------------------------
    // 用户登录信息存储 token
    String LOGIN_SESSION = APP + "login_session:";
    // 存储用户token信息
    String USER_TOKEN = APP + "user_token:";

    //---------------------socket心跳---------------------------
    String WS_USER_HEART_BEAT = APP + "ws_user_heart_beat:";
    Long HEART_BEAT_EXPIRE_TIME = 6L;

}
