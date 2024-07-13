package com.salmon.chatService.netty;

import com.salmon.chatService.constant.RedisPrefixConstant;
import com.salmon.chatService.utils.RedisUtils;
import com.salmon.chatService.utils.Utils;
import org.springframework.stereotype.Service;

/**
 * Netty相关逻辑
 *
 * @author Salmon
 * @since 2024-07-11
 */
@Service
public class NettyService {

    /**
     * 获取用户心跳
     *
     * @param account 用户账号
     * @return 时间戳
     */
    public Long getUserHeartBeat(String account) {
        return RedisUtils.get(RedisPrefixConstant.WS_USER_HEART_BEAT + account, Long.class);
    }

    /**
     * 存储用户心跳
     *
     * @param account 用户账号
     */
    public void saveUserHeartBeat(String account) {
        RedisUtils.set(RedisPrefixConstant.WS_USER_HEART_BEAT + account, Utils.getCurrentTimestampInMillis(), RedisPrefixConstant.HEART_BEAT_EXPIRE_TIME);
    }

    /**
     * 移除心跳
     *
     * @param account 用户账号
     */
    public void removeUserHeartBeat(String account) {
        RedisUtils.del(RedisPrefixConstant.WS_USER_HEART_BEAT + account);
    }
}
