package com.salmon.chatService.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.salmon.chatService.annotation.CheckAuth;
import com.salmon.chatService.common.BaseResponse;
import com.salmon.chatService.common.PageRequest;
import com.salmon.chatService.common.ResultUtils;
import com.salmon.chatService.model.dto.contact.ApplyRequest;
import com.salmon.chatService.model.dto.contact.SearchRequest;
import com.salmon.chatService.model.po.UserContactApply;
import com.salmon.chatService.model.vo.account.TokenUserVo;
import com.salmon.chatService.model.vo.contact.ApplyRecordVO;
import com.salmon.chatService.model.vo.contact.ApplyResultVO;
import com.salmon.chatService.model.vo.contact.SearchContactVO;
import com.salmon.chatService.service.UserContactApplyService;
import com.salmon.chatService.service.UserContactService;
import com.salmon.chatService.utils.UserHolder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 联系人相关API
 *
 * @author Salmon
 * @since 2024-06-09
 */
@RestController
@RequestMapping("/contact")
@Slf4j
@Tag(name = "ContactController", description = "联系人相关API")
public class ContactController {

    @Resource
    private UserContactService userContactService;
    @Resource
    private UserContactApplyService userContactApplyService;

    @Operation(summary = "搜索联系人/群")
    @PostMapping("/search")
    @CheckAuth
    public BaseResponse<SearchContactVO> search(@RequestBody @Valid SearchRequest request) {
        SearchContactVO contactVO = userContactService.search(request);
        return ResultUtils.success(contactVO);
    }

    @Operation(summary = "申请加入好友/群聊")
    @PostMapping("/applyAdd")
    @CheckAuth
    public BaseResponse<ApplyResultVO> applyAdd(@RequestBody @Valid ApplyRequest request) {
        ApplyResultVO applyResultVO = userContactService.applyAdd(request);
        return ResultUtils.success(applyResultVO);
    }

    @Operation(summary = "获取申请列表")
    @PostMapping("/loadApply")
    @CheckAuth
    public BaseResponse<Page<ApplyRecordVO>> loadApply(@RequestBody @Valid PageRequest request) {
        Page<ApplyRecordVO> recordVOPage = userContactApplyService.loadApply(request);
        return ResultUtils.success(recordVOPage);
    }
}
