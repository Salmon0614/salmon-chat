package com.salmon.chatService.utils;

import com.salmon.chatService.model.vo.account.TokenUserVo;
import lombok.extern.slf4j.Slf4j;

/**
 * 用户信息存储
 *
 * @author Salmon
 * @since 2024-06-09
 */
@Slf4j
public class UserHolder {

    private static final ThreadLocal<TokenUserVo> threadLocal = new ThreadLocal<>();

    public static void setUser(TokenUserVo tokenUserVo) {
        log.debug("setUser...");
        threadLocal.set(tokenUserVo);
    }

    public static TokenUserVo getUser() {
        log.debug("getUser...");
        return threadLocal.get();
    }

    public static void removeUser() {
        log.debug("removeUser...");
        threadLocal.remove();
    }
}
