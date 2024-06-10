package com.salmon.chatService.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.salmon.chatService.annotation.CheckAuth;
import com.salmon.chatService.common.*;
import com.salmon.chatService.exception.ThrowUtils;
import com.salmon.chatService.model.dto.contact.ApplyRequest;
import com.salmon.chatService.model.dto.contact.DealWithApplyRequest;
import com.salmon.chatService.model.dto.contact.LoadContactRequest;
import com.salmon.chatService.model.dto.contact.SearchRequest;
import com.salmon.chatService.model.enums.contact.UserContactTypeEnum;
import com.salmon.chatService.model.enums.userContact.UserContactStatusEnum;
import com.salmon.chatService.model.vo.contact.*;
import com.salmon.chatService.service.UserContactApplyService;
import com.salmon.chatService.service.UserContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

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

    @Operation(summary = "处理申请")
    @PostMapping("/dealWithApply")
    @CheckAuth
    public BaseResponse<Page<ApplyRecordVO>> dealWithApply(@RequestBody @Valid DealWithApplyRequest request) {
        userContactApplyService.dealWithApply(request);
        return ResultUtils.success();
    }

    @Operation(summary = "获取联系人列表")
    @PostMapping("/loadContact")
    @CheckAuth
    public BaseResponse<List<UserContactVO>> loadContact(@RequestBody @Valid LoadContactRequest request) {
        Integer contactType = request.getContactType();
        UserContactTypeEnum contactTypeEnum = UserContactTypeEnum.getEnumByValue(contactType);
        ThrowUtils.throwIf(Objects.isNull(contactTypeEnum), ErrorCode.PARAMS_ERROR);
        List<UserContactVO> userContactVOS = userContactService.loadContact(contactType);
        return ResultUtils.success(userContactVOS);
    }

    @Operation(summary = "获取联系人详情（不一定是好友，比如群成员里看详情））")
    @PostMapping("/getContactInfo")
    @CheckAuth
    public BaseResponse<ContactInfoVO> getContactInfo(@RequestBody @Valid IdRequest request) {
        ContactInfoVO contactInfo = userContactService.getContactInfo(request.getId());
        return ResultUtils.success(contactInfo);
    }

    @Operation(summary = "获取联系人详情（必须是好友（包含被拉黑或者被删））")
    @PostMapping("/getContactUserInfo")
    @CheckAuth
    public BaseResponse<ContactInfoVO> getContactUserInfo(@RequestBody @Valid IdRequest request) {
        ContactInfoVO contactInfo = userContactService.getContactUserInfo(request.getId());
        return ResultUtils.success(contactInfo);
    }

    @Operation(summary = "删除联系人")
    @PostMapping("/delContact")
    @CheckAuth
    public BaseResponse<ContactInfoVO> delContact(@RequestBody @Valid IdRequest request) {
        userContactService.removeUserContact(request.getId().intValue(), UserContactStatusEnum.DEL);
        return ResultUtils.success();
    }

    @Operation(summary = "拉黑联系人")
    @PostMapping("/addContactToBlackList")
    @CheckAuth
    public BaseResponse<ContactInfoVO> addContactToBlackList(@RequestBody @Valid IdRequest request) {
        userContactService.removeUserContact(request.getId().intValue(), UserContactStatusEnum.BLACK);
        return ResultUtils.success();
    }
}
