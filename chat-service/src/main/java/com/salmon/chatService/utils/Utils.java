package com.salmon.chatService.utils;

import cn.hutool.core.util.RandomUtil;
import com.salmon.chatService.model.enums.contact.UserContactTypeEnum;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

/**
 * 业务工具
 *
 * @author Salmon
 * @since 2024-06-05
 */
public class Utils {

    /**
     * 生成 token
     *
     * @param account 账号
     * @return token
     */
    public static String generateToken(String account) {
        return DigestUtils.md5DigestAsHex((account + RandomStringUtils.random(22)).getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 生成用户账号
     */
    public static String generateAccount() {
        return UserContactTypeEnum.USER.getPrefix() + RandomUtil.randomNumbers(11);
    }

    /**
     * 生成用户账号
     */
    public static String generateGroupNumber() {
        return UserContactTypeEnum.GROUP.getPrefix() + RandomUtil.randomNumbers(11);
    }

    /**
     * 生成盐
     */
    public static String getSalt() {
        return RandomStringUtils.random(15, true, true);
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

    /**
     * 判断关键字是否为手机号
     *
     * @param mobile 手机号
     */
    public static boolean isMobile(String mobile) {
        String mobilePattern = "^((1[3,5,8][0-9])|(14[5,7])|(17[0,6,7,8])|(19[7]))\\d{8}$";
        return mobile.matches(mobilePattern);
    }

    /**
     * 判断关键字是否为邮箱
     *
     * @param email 邮箱
     */
    public static boolean isEmail(String email) {
        String emailPattern = "^([a-zA-Z]|[0-9])(\\w|\\-)+@[a-zA-Z0-9]+\\.([a-zA-Z]{2,4})$";
        return email.matches(emailPattern);
    }
}
