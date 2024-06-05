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
    // 登录状态
    String LOGIN_SESSION = APP + "login_session:";
    String LOGIN_SESSION_TOKEN = APP + "login_session_token:";
    // 一天
    Long LOGIN_SESSION_EXPIRE_TIME = 24 * 60 * 60L;
    // APP 配置信息
    String APP_SYS_CONFIG_SETTING = APP + "app_sys_config_setting";
}
