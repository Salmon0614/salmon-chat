package com.salmon.chatService.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.salmon.chatService.common.*;
import com.salmon.chatService.exception.ThrowUtils;
import com.salmon.chatService.model.dto.chatSessionUser.ChatSessionUserAddRequest;
import com.salmon.chatService.model.dto.chatSessionUser.ChatSessionUserQueryRequest;
import com.salmon.chatService.model.dto.chatSessionUser.ChatSessionUserUpdateRequest;
import com.salmon.chatService.model.po.ChatSessionUser;
import com.salmon.chatService.model.vo.chatSessionUser.ChatSessionUserVO;
import com.salmon.chatService.service.ChatSessionUserService;
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
 * 聊天会话用户 前端控制器
 * </p>
 *
 * @author Salmon
 * @since 2024-07-12
 */
@RestController
@RequestMapping("/chatSessionUser")
@Slf4j
@Tag(name = "ChatSessionUserController", description = "聊天会话用户前端控制器")
public class ChatSessionUserController extends BaseController {

    @Resource
    private ChatSessionUserService chatSessionUserService;

    @Operation(summary = "添加聊天会话用户")
    @PostMapping("/add")
    public BaseResponse<?> addChatSessionUser(@RequestBody @Valid ChatSessionUserAddRequest request) {
        ChatSessionUser chatSessionUser = new ChatSessionUser();
        BeanUtils.copyProperties(request, chatSessionUser);
        ThrowUtils.throwIf(!chatSessionUserService.save(chatSessionUser), ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(chatSessionUser.getId());
    }

    @Operation(summary = "修改聊天会话用户")
    @PostMapping("/update")
    public BaseResponse<?> updateChatSessionUser(@RequestBody @Valid ChatSessionUserUpdateRequest request) {
        ChatSessionUser chatSessionUser = chatSessionUserService.getById(request.getId());
        ThrowUtils.throwIf(chatSessionUser == null, ErrorCode.NOT_FOUND_ERROR);
        BeanUtils.copyProperties(request, chatSessionUser);
        ThrowUtils.throwIf(!chatSessionUserService.updateById(chatSessionUser), ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(chatSessionUser.getId());
    }

    @Operation(summary = "删除聊天会话用户")
    @PostMapping("/delete")
    public BaseResponse<?> deleteChatSessionUser(@RequestBody @Valid DeleteRequest request) {
        ThrowUtils.throwIf(!chatSessionUserService.removeById(request.getId()), ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(); 
    }

    @Operation(summary = "根据ID查询聊天会话用户")
    @PostMapping("/getById")
    public BaseResponse<ChatSessionUserVO> getChatSessionUserById(@RequestBody @Valid IdRequest request) {
        ChatSessionUser chatSessionUser = chatSessionUserService.getById(request.getId());
        ChatSessionUserVO chatSessionUserVO = ChatSessionUserVO.objToVo(chatSessionUser);
        return ResultUtils.success(chatSessionUserVO);
    }

    @Operation(summary = "分页查询聊天会话用户")
    @PostMapping("/queryPage")
    public BaseResponse<Page<ChatSessionUser>> queryChatSessionUserPage(@RequestBody @Valid ChatSessionUserQueryRequest request) {
        long current = request.getCurrent();
        long size = request.getPageSize();
        QueryWrapper<ChatSessionUser> queryWrapper = new QueryWrapper<>();
        // todo 查询条件
        Page<ChatSessionUser> page = chatSessionUserService.page(new Page<>(current, size), queryWrapper);
        return ResultUtils.success(page);
    }
}
