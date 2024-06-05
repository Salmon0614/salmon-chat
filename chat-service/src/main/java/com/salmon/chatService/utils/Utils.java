package com.salmon.chatService.utils;

import cn.hutool.core.util.RandomUtil;
import com.salmon.chatService.model.enums.contact.UserContactTypeEnum;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.util.DigestUtils;

/**
 * 业务工具
 *
 * @author Salmon
 * @since 2024-06-05
 */
public class Utils {

    /**
     * 生成用户账号
     */
    public static String generateAccount() {
        return UserContactTypeEnum.USER.getPrefix() + RandomUtil.randomNumbers(10);
    }

    /**
     * 生成盐
     */
    public static String getSalt() {
        return RandomStringUtils.random(8, true, true);
    }

    /**
     * 加密密码
     *
     * @param password 密码
     */
    public static String encryptPassword(String password) {
        return encryptPassword(password, "");
    }


    /**
     * 加密密码
     *
     * @param password 密码
     * @param salt     盐
     */
    public static String encryptPassword(String password, String salt) {
        return DigestUtils.md5DigestAsHex((salt + password).getBytes());
    }

    /**
     * 校验密码是否输入正确
     *
     * @param password        密码
     * @param salt            盐
     * @param encryptPassword 数据库存储的密码
     */
    public static boolean checkPassword(String password, String salt, String encryptPassword) {
        return DigestUtils.md5DigestAsHex((salt + password).getBytes()).equals(encryptPassword);
    }
}
