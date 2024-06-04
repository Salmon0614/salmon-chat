package com.salmon.chatService.constant;

/**
 * Redis key 常量
 *
 * @author Salmon
 * @since 2024-06-05
 */
public interface RedisPrefixConstant {
    // 验证码
    String CHECK_CODE = "salmon_chat:account:check_code:";
    // 10分钟
    Long CODE_EXPIRE_TIME = 10 * 60L;
}
