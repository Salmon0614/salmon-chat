package com.salmon.chatService.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.salmon.chatService.annotation.CheckAuth;
import com.salmon.chatService.common.BaseResponse;
import com.salmon.chatService.common.ResultUtils;
import com.salmon.chatService.model.dto.user.UserQueryRequest;
import com.salmon.chatService.model.po.User;
import com.salmon.chatService.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
@Tag(name = "AdminController", description = "管理端前端控制器")
public class AdminController {

    @Resource
    private UserService userService;

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


}
