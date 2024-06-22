package com.salmon.chatService.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.salmon.chatService.common.*;
import com.salmon.chatService.exception.ThrowUtils;
import com.salmon.chatService.model.dto.userBeauty.UserBeautyAddRequest;
import com.salmon.chatService.model.dto.userBeauty.UserBeautyQueryRequest;
import com.salmon.chatService.model.dto.userBeauty.UserBeautyUpdateRequest;
import com.salmon.chatService.model.po.UserBeauty;
import com.salmon.chatService.model.vo.userBeauty.UserBeautyVO;
import com.salmon.chatService.service.UserBeautyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.salmon.chatService.common.BaseController;
import javax.annotation.Resource;

/**
 * <p>
 * 靓号 前端控制器
 * </p>
 *
 * @author Salmon
 * @since 2024-06-05
 */
@RestController
@RequestMapping("/userBeauty")
@Slf4j
@Tag(name = "UserBeautyController", description = "靓号前端控制器")
public class UserBeautyController extends BaseController {

    @Resource
    private UserBeautyService userBeautyService;

    @Operation(summary = "添加靓号")
    @PostMapping("/add")
    public BaseResponse<Object> addUserBeauty(@RequestBody UserBeautyAddRequest request) {
        ThrowUtils.throwIf(request == null, ErrorCode.PARAMS_ERROR);
        UserBeauty userBeauty = new UserBeauty();
        BeanUtils.copyProperties(request, userBeauty);
        ThrowUtils.throwIf(!userBeautyService.save(userBeauty), ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(userBeauty.getId());
    }

    @Operation(summary = "修改靓号")
    @PostMapping("/update")
    public BaseResponse<Object> updateUserBeauty(@RequestBody UserBeautyUpdateRequest request) {
        ThrowUtils.throwIf(request == null || request.getId() <= 0, ErrorCode.PARAMS_ERROR);
        UserBeauty userBeauty = new UserBeauty();
        BeanUtils.copyProperties(request, userBeauty);
        UpdateWrapper<UserBeauty> updateWrapper = new UpdateWrapper<>();
        updateWrapper.setEntity(userBeauty);
        ThrowUtils.throwIf(!userBeautyService.update(updateWrapper), ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(userBeauty.getId());
    }

    @Operation(summary = "删除靓号")
    @PostMapping("/delete")
    public BaseResponse<Object> deleteUserBeauty(@RequestBody DeleteRequest request) {
        ThrowUtils.throwIf(request == null || request.getId() <= 0, ErrorCode.PARAMS_ERROR);
        ThrowUtils.throwIf(!userBeautyService.removeById(request.getId()), ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(); 
    }

    @Operation(summary = "根据ID查询靓号")
    @PostMapping("/getById")
    public BaseResponse<UserBeautyVO> getUserBeautyById(@RequestBody IdRequest request) {
        UserBeauty userBeauty = userBeautyService.getById(request.getId());
        UserBeautyVO userBeautyVO = new UserBeautyVO();
        BeanUtils.copyProperties(userBeauty, userBeautyVO);
        return ResultUtils.success(userBeautyVO);
    }

    @Operation(summary = "分页查询靓号")
    @PostMapping("/queryPage")
    public BaseResponse<Page<UserBeauty>> queryUserBeautyPage(@RequestBody UserBeautyQueryRequest request) {
        long current = request.getCurrent();
        long size = request.getPageSize();
        QueryWrapper<UserBeauty> queryWrapper = new QueryWrapper<>();
        // todo 查询条件
        Page<UserBeauty> page = userBeautyService.page(new Page<>(current, size), queryWrapper);
        return ResultUtils.success(page);
    }
}
