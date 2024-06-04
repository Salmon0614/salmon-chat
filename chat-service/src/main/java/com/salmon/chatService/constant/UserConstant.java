package com.salmon.chatService.constant;

/**
 * 用户常量
 *
 * @author Salmon
 * @since 2024-05-21
 */
public interface UserConstant {

    Integer MIN_ACCOUNT_LENGTH = 4;
    Integer MIN_PASSWORD_LENGTH = 8;

    Integer DEFAULT_GENDER=1;
    String DEFAULT_AVATAR="https://tupian.qqw21.com/article/UploadPic/2020-6/20206921521241233.jpg";
    String PASSWORD_SALT = "1d5q,.14w6d41x";
    String AS_KEY_SALT = "d821uda9j;sq'21";

    /**
     * 用户登录状态标识
     */
    String USER_LOGIN_STATE = "user_login";

    /**
     * 默认角色
     */
    String DEFAULT_ROLE = "user";

    /**
     * 管理员角色
     */
    String ADMIN_ROLE = "admin";

    /**
     * 被封号
     */
    String BAN_ROLE = "ban";
}
