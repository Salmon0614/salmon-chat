package com.salmon.chatService.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.salmon.chatService.annotation.CheckAuth;
import com.salmon.chatService.common.BaseResponse;
import com.salmon.chatService.common.ErrorCode;
import com.salmon.chatService.common.IdRequest;
import com.salmon.chatService.common.ResultUtils;
import com.salmon.chatService.exception.ThrowUtils;
import com.salmon.chatService.model.dto.admin.SystemConfigRequest;
import com.salmon.chatService.model.dto.admin.UpdateUserStatusRequest;
import com.salmon.chatService.model.dto.group.GroupQueryRequest;
import com.salmon.chatService.model.dto.user.UserQueryRequest;
import com.salmon.chatService.model.po.Group;
import com.salmon.chatService.model.po.User;
import com.salmon.chatService.model.vo.app.SystemConfigVo;
import com.salmon.chatService.model.vo.group.GroupVO;
import com.salmon.chatService.service.AppService;
import com.salmon.chatService.service.GroupService;
import com.salmon.chatService.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Objects;

/**
 * <p>
 * 管理端 前端控制器
 * </p>
 *
 * @author Salmon
 * @since 2024-06-23
 */
@RestController
@RequestMapping("/admin")
@Slf4j
@AllArgsConstructor
@Tag(name = "AdminController", description = "管理端前端控制器")
public class AdminController {

    private final UserService userService;
    private final GroupService groupService;
    private final AppService appService;
    @Operation(summary = "分页查询用户")
    @PostMapping("/queryUserPage")
    @CheckAuth(needAdmin = true)
    public BaseResponse<Page<User>> queryUserPage(@RequestBody UserQueryRequest request) {
        long current = request.getCurrent();
        long size = request.getPageSize();
        User queryUser = new User();
        BeanUtils.copyProperties(request, queryUser);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>(queryUser);
        queryWrapper.orderByDesc(User::getCreateTime);
        Page<User> page = userService.page(new Page<>(current, size), queryWrapper);
        return ResultUtils.success(page);
    }

    @Operation(summary = "更新用户状态")
    @PostMapping("/updateUserStatus")
    @CheckAuth(needAdmin = true)
    public BaseResponse<?> updateUserStatus(@RequestBody @Valid UpdateUserStatusRequest request) {
        userService.updateUserStatus(request);
        return ResultUtils.success();
    }


    @Operation(summary = "强制用户下线")
    @PostMapping("/forceOffLine")
    @CheckAuth(needAdmin = true)
    public BaseResponse<?> forceOffLine(@RequestBody @Valid IdRequest request) {
        userService.forceOffLine(request.getId().intValue());
        return ResultUtils.success();
    }

    @Operation(summary = "分页查询群聊")
    @PostMapping("/queryGroupPage")
    @CheckAuth(needAdmin = true)
    public BaseResponse<Page<GroupVO>> queryGroupPage(@RequestBody GroupQueryRequest request) {
        Page<GroupVO> page = groupService.queryGroupVOPage(request);
        return ResultUtils.success(page);
    }

    @Operation(summary = "解散群聊")
    @PostMapping("/dissolutionGroup")
    @CheckAuth(needAdmin = true)
    public BaseResponse<Page<GroupVO>> dissolutionGroup(@RequestBody @Valid IdRequest request) {
        Group group = groupService.getById(request.getId());
        ThrowUtils.throwIf(Objects.isNull(group), ErrorCode.OPERATION_ERROR);
        groupService.dissolutionGroup(group.getGroupOwnerId(), group.getId());
        return ResultUtils.success();
    }

    @Operation(summary = "获取系统设置")
    @PostMapping("/getSysSetting")
    @CheckAuth(needAdmin = true)
    public BaseResponse<SystemConfigVo> getSysSetting() {
        SystemConfigVo systemConfig = appService.getSystemConfig();
        return ResultUtils.success(systemConfig);
    }

    @Operation(summary = "保存系统设置")
    @PostMapping("/saveSysSetting")
    @CheckAuth(needAdmin = true)
    public BaseResponse<SystemConfigVo> saveSysSetting(@RequestBody SystemConfigRequest request) {
        // todo 上传机器人的头像、封面
        appService.saveOrUpdateSystemConfig(request);
        return ResultUtils.success();
    }
}
