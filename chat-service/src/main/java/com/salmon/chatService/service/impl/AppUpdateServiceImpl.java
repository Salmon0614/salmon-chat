package com.salmon.chatService.service.impl;

import com.salmon.chatService.model.po.AppUpdate;
import com.salmon.chatService.mapper.AppUpdateMapper;
import com.salmon.chatService.service.AppUpdateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * APP发布 服务实现类
 * </p>
 *
 * @author Salmon
 * @since 2024-07-10
 */
@Service
public class AppUpdateServiceImpl extends ServiceImpl<AppUpdateMapper, AppUpdate> implements AppUpdateService {

    /**
     * 检查版本更新
     *
     * @param userId     用户ID
     * @param appVersion 当前的app版本
     * @return 最新版的APP信息
     */
    @Override
    public AppUpdate checkVersion(Integer userId, String appVersion) {
        return this.baseMapper.checkVersion(userId, appVersion);
    }
}
