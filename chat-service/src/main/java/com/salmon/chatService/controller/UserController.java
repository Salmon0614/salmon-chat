package com.salmon.chatService.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.salmon.chatService.annotation.CheckAuth;
import com.salmon.chatService.common.*;
import com.salmon.chatService.exception.ThrowUtils;
import com.salmon.chatService.model.dto.user.*;
import com.salmon.chatService.model.po.User;
import com.salmon.chatService.model.vo.account.TokenUserVo;
import com.salmon.chatService.model.vo.user.UserVO;
import com.salmon.chatService.service.UserService;
import com.salmon.chatService.utils.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.salmon.chatService.common.BaseController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author Salmon
 * @since 2024-06-04
 */
@RestController
@RequestMapping("/user")
@Slf4j
@Tag(name = "UserController", description = "用户前端控制器")
public class UserController extends BaseController {

    @Resource
    private UserService userService;

    @Operation(summary = "保存当前登录用户信息")
    @PostMapping("/saveUserInfo")
    @CheckAuth
    public BaseResponse<UserVO> saveUserInfo(@RequestBody @Valid UserSaveRequest request) {
        UserVO userVO = userService.updateUserInfo(request);
        return ResultUtils.success(userVO);
    }

    @Operation(summary = "获取当前登录用户信息")
    @PostMapping("/getUserInfo")
    @CheckAuth
    public BaseResponse<UserVO> getUserInfo() {
        TokenUserVo tokenUserVo = UserHolder.getUser();
        User user = userService.getById(tokenUserVo.getId());
        return ResultUtils.success(UserVO.objToVo(user));
    }

    @Operation(summary = "更新当前登录用户密码")
    @PostMapping("/updatePassword")
    @CheckAuth
    public BaseResponse<?> updatePassword(@RequestBody @Valid UpdatePassword request) {
        userService.updatePassword(request);
        return ResultUtils.success();
    }

    @Operation(summary = "添加用户")
    @PostMapping("/add")
    public BaseResponse<Object> addUser(@RequestBody UserAddRequest request) {
        ThrowUtils.throwIf(request == null, ErrorCode.PARAMS_ERROR);
        User user = new User();
        BeanUtils.copyProperties(request, user);
        ThrowUtils.throwIf(!userService.save(user), ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(user.getId());
    }

    @Operation(summary = "修改用户")
    @PostMapping("/update")
    public BaseResponse<Object> updateUser(@RequestBody UserUpdateRequest request) {
        ThrowUtils.throwIf(request == null || request.getId() <= 0, ErrorCode.PARAMS_ERROR);
        User user = new User();
        BeanUtils.copyProperties(request, user);
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>(user);
        ThrowUtils.throwIf(!userService.update(updateWrapper), ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(user.getId());
    }

    @Operation(summary = "删除用户")
    @PostMapping("/delete")
    public BaseResponse<Object> deleteUser(@RequestBody DeleteRequest request) {
        ThrowUtils.throwIf(request == null || request.getId() <= 0, ErrorCode.PARAMS_ERROR);
        ThrowUtils.throwIf(!userService.removeById(request.getId()), ErrorCode.OPERATION_ERROR);
        return ResultUtils.success();
    }

    @Operation(summary = "根据ID查询用户")
    @PostMapping("/getById")
    public BaseResponse<UserVO> getUserById(@RequestBody IdRequest request) {
        User user = userService.getById(request.getId());
        UserVO userVO = UserVO.objToVo(user);
        return ResultUtils.success(userVO);
    }

    @Operation(summary = "分页查询用户")
    @PostMapping("/queryPage")
    public BaseResponse<Page<User>> queryUserPage(@RequestBody UserQueryRequest request) {
        long current = request.getCurrent();
        long size = request.getPageSize();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // todo 查询条件
        Page<User> page = userService.page(new Page<>(current, size), queryWrapper);
        return ResultUtils.success(page);
    }
}
