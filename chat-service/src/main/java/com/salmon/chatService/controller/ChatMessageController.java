package com.salmon.chatService.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.salmon.chatService.common.*;
import com.salmon.chatService.exception.ThrowUtils;
import com.salmon.chatService.model.dto.chatMessage.ChatMessageAddRequest;
import com.salmon.chatService.model.dto.chatMessage.ChatMessageQueryRequest;
import com.salmon.chatService.model.dto.chatMessage.ChatMessageUpdateRequest;
import com.salmon.chatService.model.po.ChatMessage;
import com.salmon.chatService.model.vo.chatMessage.ChatMessageVO;
import com.salmon.chatService.service.ChatMessageService;
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
 * 聊天消息 前端控制器
 * </p>
 *
 * @author Salmon
 * @since 2024-07-12
 */
@RestController
@RequestMapping("/chatMessage")
@Slf4j
@Tag(name = "ChatMessageController", description = "聊天消息前端控制器")
public class ChatMessageController extends BaseController {

    @Resource
    private ChatMessageService chatMessageService;

    @Operation(summary = "添加聊天消息")
    @PostMapping("/add")
    public BaseResponse<?> addChatMessage(@RequestBody @Valid ChatMessageAddRequest request) {
        ChatMessage chatMessage = new ChatMessage();
        BeanUtils.copyProperties(request, chatMessage);
        ThrowUtils.throwIf(!chatMessageService.save(chatMessage), ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(chatMessage.getId());
    }

    @Operation(summary = "修改聊天消息")
    @PostMapping("/update")
    public BaseResponse<?> updateChatMessage(@RequestBody @Valid ChatMessageUpdateRequest request) {
        ChatMessage chatMessage = chatMessageService.getById(request.getId());
        ThrowUtils.throwIf(chatMessage == null, ErrorCode.NOT_FOUND_ERROR);
        BeanUtils.copyProperties(request, chatMessage);
        ThrowUtils.throwIf(!chatMessageService.updateById(chatMessage), ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(chatMessage.getId());
    }

    @Operation(summary = "删除聊天消息")
    @PostMapping("/delete")
    public BaseResponse<?> deleteChatMessage(@RequestBody @Valid DeleteRequest request) {
        ThrowUtils.throwIf(!chatMessageService.removeById(request.getId()), ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(); 
    }

    @Operation(summary = "根据ID查询聊天消息")
    @PostMapping("/getById")
    public BaseResponse<ChatMessageVO> getChatMessageById(@RequestBody @Valid IdRequest request) {
        ChatMessage chatMessage = chatMessageService.getById(request.getId());
        ChatMessageVO chatMessageVO = ChatMessageVO.objToVo(chatMessage);
        return ResultUtils.success(chatMessageVO);
    }

    @Operation(summary = "分页查询聊天消息")
    @PostMapping("/queryPage")
    public BaseResponse<Page<ChatMessage>> queryChatMessagePage(@RequestBody @Valid ChatMessageQueryRequest request) {
        long current = request.getCurrent();
        long size = request.getPageSize();
        QueryWrapper<ChatMessage> queryWrapper = new QueryWrapper<>();
        // todo 查询条件
        Page<ChatMessage> page = chatMessageService.page(new Page<>(current, size), queryWrapper);
        return ResultUtils.success(page);
    }
}
