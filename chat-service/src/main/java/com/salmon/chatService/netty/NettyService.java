package com.salmon.chatService.netty;

import com.salmon.chatService.constant.RedisPrefixConstant;
import com.salmon.chatService.utils.RedisUtils;
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
     * @param userId 用户ID
     * @return 时间戳
     */
    public Long getUserHeartBeat(Integer userId) {
        return RedisUtils.get(RedisPrefixConstant.WS_USER_HEART_BEAT + userId, Long.class);
    }

    /**
     * 存储用户心跳
     *
     * @param userId 用户ID
     */
    public void saveUserHeartBeat(Integer userId) {
        RedisUtils.set(RedisPrefixConstant.WS_USER_HEART_BEAT + userId, System.currentTimeMillis(), RedisPrefixConstant.HEART_BEAT_EXPIRE_TIME);
    }

}
