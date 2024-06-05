package com.salmon.chatService.constant;

import com.salmon.chatService.model.enums.user.UserGenderEnum;

/**
 * 用户常量
 *
 * @author Salmon
 * @since 2024-05-21
 */
public interface UserConstant {

    Integer MIN_PASSWORD_LENGTH = 8;
    UserGenderEnum DEFAULT_GENDER = UserGenderEnum.MAN;
    String DEFAULT_AVATAR = "https://tupian.qqw21.com/article/UploadPic/2020-6/20206921521241233.jpg";
}
