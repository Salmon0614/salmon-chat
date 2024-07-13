package com.salmon.chatService.utils;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.salmon.chatService.constant.Settings;
import com.salmon.chatService.model.enums.contact.UserContactTypeEnum;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;


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
        return encodeMd5(salt + password);
    }

    /**
     * md5摘要编码
     *
     * @param content 内容
     * @return md5串
     */
    public static String encodeMd5(String content) {
        return DigestUtils.md5DigestAsHex(content.getBytes());
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

    /**
     * 清理并替换HTML标签，防止sql注入
     *
     * @param content 待处理字符串
     * @return 转义
     */
    public static String cleanHtmlTag(String content) {
        if (!StringUtils.hasText(content)) {
            return null;
        }
        content = content.replace("<", "&lt;");
        content = content.replace("\r\n", "<br>");
        content = content.replace("\n", "<br>");
        return content;
    }


    /**
     * 生成sessionId
     *
     * @param accounts 账号集合
     * @return sessionId
     */
    public static String generateChatSessionId(String[] accounts) {
        Arrays.sort(accounts);
        return encodeMd5(StrUtil.join(",", Arrays.asList(accounts)));
    }

    /**
     * 获取时间戳（毫秒级）
     *
     * @return 时间戳
     */
    public static long getCurrentTimestampInMillis() {
        LocalDateTime now = LocalDateTime.now();
        ZonedDateTime zdt = now.atZone(ZoneId.of(Settings.ZONE_ID));
        return zdt.toInstant().toEpochMilli();
    }

    /**
     * 基于时间戳转为localDateTime
     *
     * @param timestamp 时间戳
     * @return localDateTime
     */
    public static LocalDateTime convertTimestampToLocalDateTime(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        return LocalDateTime.ofInstant(instant, ZoneId.of(Settings.ZONE_ID));
    }
}
