package com.salmon.chatService.service;

import com.salmon.chatService.model.po.AppUpdate;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * APP发布 服务类
 * </p>
 *
 * @author Salmon
 * @since 2024-07-10
 */
public interface AppUpdateService extends IService<AppUpdate> {

    /**
     * 检查版本更新
     *
     * @param userId     用户ID
     * @param appVersion 当前的app版本
     * @return 最新版的APP信息
     */
    AppUpdate checkVersion(Integer userId, String appVersion);
}
