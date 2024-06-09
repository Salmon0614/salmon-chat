package com.salmon.chatService.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.salmon.chatService.common.*;
import com.salmon.chatService.exception.ThrowUtils;
import com.salmon.chatService.model.dto.userContact.UserContactAddRequest;
import com.salmon.chatService.model.dto.userContact.UserContactQueryRequest;
import com.salmon.chatService.model.dto.userContact.UserContactUpdateRequest;
import com.salmon.chatService.model.po.UserContact;
import com.salmon.chatService.model.vo.userContact.UserContactVO;
import com.salmon.chatService.service.UserContactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.salmon.chatService.common.BaseController;

import javax.annotation.Resource;

/**
 * <p>
 * 联系人 前端控制器
 * </p>
 *
 * @author Salmon
 * @since 2024-06-08
 */
@RestController
@RequestMapping("/userContact")
@Slf4j
@Tag(name = "UserContactController", description = "联系人前端控制器")
public class UserContactController extends BaseController {

    @Resource
    private UserContactService userContactService;

    @Operation(summary = "添加联系人")
    @PostMapping("/add")
    public BaseResponse<Object> addUserContact(@RequestBody UserContactAddRequest request) {
        ThrowUtils.throwIf(request == null, ErrorCode.PARAMS_ERROR);
        UserContact userContact = new UserContact();
        BeanUtils.copyProperties(request, userContact);
        ThrowUtils.throwIf(!userContactService.save(userContact), ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(userContact.getId());
    }

    @Operation(summary = "修改联系人")
    @PostMapping("/update")
    public BaseResponse<Object> updateUserContact(@RequestBody UserContactUpdateRequest request) {
        ThrowUtils.throwIf(request == null || request.getId() <= 0, ErrorCode.PARAMS_ERROR);
        UserContact userContact = new UserContact();
        BeanUtils.copyProperties(request, userContact);
        UpdateWrapper<UserContact> updateWrapper = new UpdateWrapper<>(userContact);
        ThrowUtils.throwIf(!userContactService.update(updateWrapper), ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(userContact.getId());
    }

    @Operation(summary = "删除联系人")
    @PostMapping("/delete")
    public BaseResponse<Object> deleteUserContact(@RequestBody DeleteRequest request) {
        ThrowUtils.throwIf(request == null || request.getId() <= 0, ErrorCode.PARAMS_ERROR);
        ThrowUtils.throwIf(!userContactService.removeById(request.getId()), ErrorCode.OPERATION_ERROR);
        return ResultUtils.success();
    }

    @Operation(summary = "根据ID查询联系人")
    @PostMapping("/getById")
    public BaseResponse<UserContactVO> getUserContactById(@RequestBody IdRequest request) {
        UserContact userContact = userContactService.getById(request.getId());
        UserContactVO userContactVO = new UserContactVO();
        BeanUtils.copyProperties(userContact, userContactVO);
        return ResultUtils.success(userContactVO);
    }

    @Operation(summary = "分页查询联系人")
    @PostMapping("/queryPage")
    public BaseResponse<Page<UserContact>> queryUserContactPage(@RequestBody UserContactQueryRequest request) {
        long current = request.getCurrent();
        long size = request.getPageSize();
        QueryWrapper<UserContact> queryWrapper = new QueryWrapper<>();
        // todo 查询条件
        Page<UserContact> page = userContactService.page(new Page<>(current, size), queryWrapper);
        return ResultUtils.success(page);
    }
}
