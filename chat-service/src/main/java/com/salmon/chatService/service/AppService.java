package com.salmon.chatService.service;

import com.salmon.chatService.model.dto.admin.SystemConfigRequest;
import com.salmon.chatService.model.vo.app.SystemConfigVo;

/**
 * <p>
 * APP 服务类
 * </p>
 *
 * @author Salmon
 * @since 2024-06-06
 */
public interface AppService {

    /**
     * 获取APP配置参数
     *
     * @return 配置信息
     */
    SystemConfigVo getSystemConfig();


    /**
     * 保存或更新配置参数
     *
     * @param systemConfigRequest 配置信息请求
     */
    void saveOrUpdateSystemConfig(SystemConfigRequest systemConfigRequest);
}
