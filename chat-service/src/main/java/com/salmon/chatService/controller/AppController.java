package com.salmon.chatService.controller;

import com.salmon.chatService.annotation.CheckAuth;
import com.salmon.chatService.common.BaseResponse;
import com.salmon.chatService.common.ResultUtils;
import com.salmon.chatService.model.dto.admin.SystemConfigRequest;
import com.salmon.chatService.model.vo.app.SystemConfigVo;
import com.salmon.chatService.service.AppService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * APP 前端控制器
 * </p>
 *
 * @author Salmon
 * @since 2024-06-05
 */
@RestController
@RequestMapping("/app")
@Slf4j
@Tag(name = "AppController", description = "APP 前端控制器")
public class AppController {

    @Resource
    private AppService appService;

    @Operation(summary = "获取APP系统配置参数")
    @PostMapping("/getSystemConfig")
    @CheckAuth(needAdmin = true)
    public BaseResponse<SystemConfigVo> getSystemConfig() {
        SystemConfigVo systemConfig = appService.getSystemConfig();
        return ResultUtils.success(systemConfig);
    }

    @Operation(summary = "保存APP系统配置参数")
    @PostMapping("/saveSystemConfig")
    @CheckAuth(needAdmin = true)
    public BaseResponse<SystemConfigVo> saveSystemConfig(@RequestBody SystemConfigRequest systemConfigRequest) {
        appService.saveOrUpdateSystemConfig(systemConfigRequest);
        return ResultUtils.success(new SystemConfigVo());
    }

}
