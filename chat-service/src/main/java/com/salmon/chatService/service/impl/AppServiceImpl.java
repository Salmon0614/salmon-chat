package com.salmon.chatService.service.impl;

import com.salmon.chatService.constant.RedisPrefixConstant;
import com.salmon.chatService.model.dto.admin.SystemConfigRequest;
import com.salmon.chatService.model.vo.app.SystemConfigVo;
import com.salmon.chatService.service.AppService;
import com.salmon.chatService.utils.RedisUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 * APP 服务类实现
 * </p>
 *
 * @author Salmon
 * @since 2024-06-06
 */
@Service
public class AppServiceImpl implements AppService {

    /**
     * 获取APP配置参数
     *
     * @return 配置信息
     */
    @Override
    public SystemConfigVo getSystemConfig() {
        SystemConfigVo systemConfigVo = RedisUtils.get(RedisPrefixConstant.APP_SYS_CONFIG_SETTING, SystemConfigVo.class);
        return Objects.nonNull(systemConfigVo) ? systemConfigVo : new SystemConfigVo();
    }

    /**
     * 保存或更新配置参数
     *
     * @param systemConfigRequest 配置信息请求
     */
    @Override
    public void saveOrUpdateSystemConfig(SystemConfigRequest systemConfigRequest) {
        SystemConfigVo systemConfigVo = new SystemConfigVo();
        BeanUtils.copyProperties(systemConfigRequest, systemConfigVo);
        RedisUtils.set(RedisPrefixConstant.APP_SYS_CONFIG_SETTING, systemConfigVo);
    }
}
