package com.salmon.chatService.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.salmon.chatService.common.*;
import com.salmon.chatService.exception.ThrowUtils;
import com.salmon.chatService.model.dto.chatSession.ChatSessionAddRequest;
import com.salmon.chatService.model.dto.chatSession.ChatSessionQueryRequest;
import com.salmon.chatService.model.dto.chatSession.ChatSessionUpdateRequest;
import com.salmon.chatService.model.po.ChatSession;
import com.salmon.chatService.model.vo.chatSession.ChatSessionVO;
import com.salmon.chatService.service.ChatSessionService;
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
 * 聊天会话信息 前端控制器
 * </p>
 *
 * @author Salmon
 * @since 2024-07-12
 */
@RestController
@RequestMapping("/chatSession")
@Slf4j
@Tag(name = "ChatSessionController", description = "聊天会话信息前端控制器")
public class ChatSessionController extends BaseController {

    @Resource
    private ChatSessionService chatSessionService;

    @Operation(summary = "添加聊天会话信息")
    @PostMapping("/add")
    public BaseResponse<?> addChatSession(@RequestBody @Valid ChatSessionAddRequest request) {
        ChatSession chatSession = new ChatSession();
        BeanUtils.copyProperties(request, chatSession);
        ThrowUtils.throwIf(!chatSessionService.save(chatSession), ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(chatSession.getId());
    }

    @Operation(summary = "修改聊天会话信息")
    @PostMapping("/update")
    public BaseResponse<?> updateChatSession(@RequestBody @Valid ChatSessionUpdateRequest request) {
        ChatSession chatSession = chatSessionService.getById(request.getId());
        ThrowUtils.throwIf(chatSession == null, ErrorCode.NOT_FOUND_ERROR);
        BeanUtils.copyProperties(request, chatSession);
        ThrowUtils.throwIf(!chatSessionService.updateById(chatSession), ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(chatSession.getId());
    }

    @Operation(summary = "删除聊天会话信息")
    @PostMapping("/delete")
    public BaseResponse<?> deleteChatSession(@RequestBody @Valid DeleteRequest request) {
        ThrowUtils.throwIf(!chatSessionService.removeById(request.getId()), ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(); 
    }

    @Operation(summary = "根据ID查询聊天会话信息")
    @PostMapping("/getById")
    public BaseResponse<ChatSessionVO> getChatSessionById(@RequestBody @Valid IdRequest request) {
        ChatSession chatSession = chatSessionService.getById(request.getId());
        ChatSessionVO chatSessionVO = ChatSessionVO.objToVo(chatSession);
        return ResultUtils.success(chatSessionVO);
    }

    @Operation(summary = "分页查询聊天会话信息")
    @PostMapping("/queryPage")
    public BaseResponse<Page<ChatSession>> queryChatSessionPage(@RequestBody @Valid ChatSessionQueryRequest request) {
        long current = request.getCurrent();
        long size = request.getPageSize();
        QueryWrapper<ChatSession> queryWrapper = new QueryWrapper<>();
        // todo 查询条件
        Page<ChatSession> page = chatSessionService.page(new Page<>(current, size), queryWrapper);
        return ResultUtils.success(page);
    }
}
