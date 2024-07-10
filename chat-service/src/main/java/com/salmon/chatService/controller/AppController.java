package com.salmon.chatService.controller;

import com.salmon.chatService.annotation.CheckAuth;
import com.salmon.chatService.common.BaseResponse;
import com.salmon.chatService.common.ResultUtils;
import com.salmon.chatService.model.dto.appUpdate.CheckVersionRequest;
import com.salmon.chatService.model.po.AppUpdate;
import com.salmon.chatService.model.vo.account.TokenUserVo;
import com.salmon.chatService.model.vo.app.SystemConfigVo;
import com.salmon.chatService.model.vo.appUpdate.AppUpdateVO;
import com.salmon.chatService.service.AppUpdateService;
import com.salmon.chatService.utils.UserHolder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

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
    private AppUpdateService appUpdateService;

    @Operation(summary = "更新检测")
    @PostMapping("/checkVersion")
    @CheckAuth()
    public BaseResponse<AppUpdateVO> checkVersion(@RequestBody @Valid CheckVersionRequest request) {
        TokenUserVo tokenUserVo = UserHolder.getUser();
        Integer userId = tokenUserVo.getId();
        AppUpdate appUpdate = appUpdateService.checkVersion(userId, request.getAppVersion());
        AppUpdateVO appUpdateVO = AppUpdateVO.objToVo(appUpdate);
        return ResultUtils.success(appUpdateVO);
    }

}
