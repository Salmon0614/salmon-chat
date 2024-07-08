package com.salmon.chatService.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.salmon.chatService.common.*;
import com.salmon.chatService.exception.ThrowUtils;
import com.salmon.chatService.model.dto.userContactApply.UserContactApplyAddRequest;
import com.salmon.chatService.model.dto.userContactApply.UserContactApplyQueryRequest;
import com.salmon.chatService.model.dto.userContactApply.UserContactApplyUpdateRequest;
import com.salmon.chatService.model.po.UserContactApply;
import com.salmon.chatService.model.vo.userContactApply.UserContactApplyVO;
import com.salmon.chatService.service.UserContactApplyService;
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
 * 联系人申请 前端控制器
 * </p>
 *
 * @author Salmon
 * @since 2024-06-08
 */
@RestController
@RequestMapping("/userContactApply")
@Slf4j
@Tag(name = "UserContactApplyController", description = "联系人申请前端控制器")
public class UserContactApplyController extends BaseController {

    @Resource
    private UserContactApplyService userContactApplyService;

    @Operation(summary = "添加联系人申请")
    @PostMapping("/add")
    public BaseResponse<Object> addUserContactApply(@RequestBody UserContactApplyAddRequest request) {
        ThrowUtils.throwIf(request == null, ErrorCode.PARAMS_ERROR);
        UserContactApply userContactApply = new UserContactApply();
        BeanUtils.copyProperties(request, userContactApply);
        ThrowUtils.throwIf(!userContactApplyService.save(userContactApply), ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(userContactApply.getId());
    }

    @Operation(summary = "修改联系人申请")
    @PostMapping("/update")
    public BaseResponse<Object> updateUserContactApply(@RequestBody UserContactApplyUpdateRequest request) {
        ThrowUtils.throwIf(request == null || request.getId() <= 0, ErrorCode.PARAMS_ERROR);
        UserContactApply userContactApply = new UserContactApply();
        BeanUtils.copyProperties(request, userContactApply);
        UpdateWrapper<UserContactApply> updateWrapper = new UpdateWrapper<>();
        updateWrapper.setEntity(userContactApply);
        ThrowUtils.throwIf(!userContactApplyService.update(updateWrapper), ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(userContactApply.getId());
    }

    @Operation(summary = "删除联系人申请")
    @PostMapping("/delete")
    public BaseResponse<Object> deleteUserContactApply(@RequestBody DeleteRequest request) {
        ThrowUtils.throwIf(request == null || request.getId() <= 0, ErrorCode.PARAMS_ERROR);
        ThrowUtils.throwIf(!userContactApplyService.removeById(request.getId()), ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(); 
    }

    @Operation(summary = "根据ID查询联系人申请")
    @PostMapping("/getById")
    public BaseResponse<UserContactApplyVO> getUserContactApplyById(@RequestBody @Valid IdRequest request) {
        UserContactApply userContactApply = userContactApplyService.getById(request.getId());
        UserContactApplyVO userContactApplyVO = new UserContactApplyVO();
        BeanUtils.copyProperties(userContactApply, userContactApplyVO);
        return ResultUtils.success(userContactApplyVO);
    }

    @Operation(summary = "分页查询联系人申请")
    @PostMapping("/queryPage")
    public BaseResponse<Page<UserContactApply>> queryUserContactApplyPage(@RequestBody UserContactApplyQueryRequest request) {
        long current = request.getCurrent();
        long size = request.getPageSize();
        QueryWrapper<UserContactApply> queryWrapper = new QueryWrapper<>();
        // todo 查询条件
        Page<UserContactApply> page = userContactApplyService.page(new Page<>(current, size), queryWrapper);
        return ResultUtils.success(page);
    }
}
