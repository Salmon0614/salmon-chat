package com.salmon.chatService.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.salmon.chatService.annotation.CheckAuth;
import com.salmon.chatService.common.*;
import com.salmon.chatService.exception.BusinessException;
import com.salmon.chatService.exception.ThrowUtils;
import com.salmon.chatService.model.dto.userBeauty.UserBeautyAddRequest;
import com.salmon.chatService.model.dto.userBeauty.UserBeautyQueryRequest;
import com.salmon.chatService.model.dto.userBeauty.UserBeautyUpdateRequest;
import com.salmon.chatService.model.enums.user.AccountBeautyStatusEnum;
import com.salmon.chatService.model.po.User;
import com.salmon.chatService.model.po.UserBeauty;
import com.salmon.chatService.service.UserBeautyService;
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
import javax.validation.Valid;

/**
 * <p>
 * 靓号 前端控制器
 * </p>
 *
 * @author Salmon
 * @since 2024-06-05
 */
@RestController
@RequestMapping("/admin/userBeauty")
@Slf4j
@Tag(name = "UserBeautyController", description = "靓号前端控制器")
public class UserBeautyController extends BaseController {

    @Resource
    private UserBeautyService userBeautyService;
    @Resource
    private UserService userService;

    @Operation(summary = "添加靓号")
    @PostMapping("/add")
    @CheckAuth(needAdmin = true)
    public BaseResponse<Object> addUserBeauty(@RequestBody @Valid UserBeautyAddRequest request) {
        LambdaQueryWrapper<UserBeauty> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserBeauty::getEmail, request.getEmail());
        if (userBeautyService.count(queryWrapper) > 0) {
            throw new BusinessException("已经设置过该邮箱靓号！");
        }
        checkBeautyAccountReq(request.getAccount());
        queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserBeauty::getAccount, request.getAccount());
        if (userBeautyService.count(queryWrapper) > 0) {
            throw new BusinessException("已存在重复靓号！");
        }
        UserBeauty userBeauty = new UserBeauty();
        BeanUtils.copyProperties(request, userBeauty);
        userBeauty.setStatus(AccountBeautyStatusEnum.UNUSED.getValue());
        ThrowUtils.throwIf(!userBeautyService.save(userBeauty), ErrorCode.OPERATION_ERROR);
        return ResultUtils.success();
    }

    @Operation(summary = "修改靓号")
    @PostMapping("/update")
    @CheckAuth(needAdmin = true)
    public BaseResponse<Object> updateUserBeauty(@RequestBody @Valid UserBeautyUpdateRequest request) {
        UserBeauty userBeauty = userBeautyService.getById(request.getId());
        ThrowUtils.throwIf(userBeauty.getStatus() == AccountBeautyStatusEnum.USED.getValue(), ErrorCode.OPERATION_ERROR, "该靓号已被使用，禁止修改！");
        checkBeautyAccountReq(request.getAccount());
        LambdaQueryWrapper<UserBeauty> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserBeauty::getAccount, request.getAccount());
        queryWrapper.ne(UserBeauty::getEmail, userBeauty.getEmail());
        if (userBeautyService.count(queryWrapper) > 0) {
            throw new BusinessException("已存在重复靓号！");
        }
        BeanUtils.copyProperties(request, userBeauty);
        ThrowUtils.throwIf(!userBeautyService.updateById(userBeauty), ErrorCode.OPERATION_ERROR);
        return ResultUtils.success();
    }

    /**
     * 校验靓号是否已经被注册
     *
     * @param account 靓号
     */
    private void checkBeautyAccountReq(String account) {
        if (userService.count(new LambdaQueryWrapper<User>().eq(User::getAccount, account)) > 0) {
            throw new BusinessException("该靓号已被注册使用！");
        }
    }

    @Operation(summary = "删除靓号")
    @PostMapping("/delete")
    @CheckAuth(needAdmin = true)
    public BaseResponse<Object> deleteUserBeauty(@RequestBody @Valid DeleteRequest request) {
        UserBeauty userBeauty = userBeautyService.getById(request.getId());
        ThrowUtils.throwIf(userBeauty.getStatus() == AccountBeautyStatusEnum.USED.getValue(), ErrorCode.OPERATION_ERROR, "该靓号已被使用，禁止删除！");
        ThrowUtils.throwIf(!userBeautyService.removeById(request.getId()), ErrorCode.OPERATION_ERROR);
        return ResultUtils.success();
    }

    @Operation(summary = "分页查询靓号")
    @PostMapping("/queryPage")
    @CheckAuth(needAdmin = true)
    public BaseResponse<Page<UserBeauty>> queryUserBeautyPage(@RequestBody UserBeautyQueryRequest request) {
        long current = request.getCurrent();
        long size = request.getPageSize();
        UserBeauty query = new UserBeauty();
        BeanUtils.copyProperties(request, query);
        LambdaQueryWrapper<UserBeauty> queryWrapper = new LambdaQueryWrapper<>(query);
        queryWrapper.orderByDesc(UserBeauty::getCreateTime);
        Page<UserBeauty> page = userBeautyService.page(new Page<>(current, size), queryWrapper);
        return ResultUtils.success(page);
    }
}
