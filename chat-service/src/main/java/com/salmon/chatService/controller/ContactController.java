package com.salmon.chatService.controller;

import com.salmon.chatService.annotation.CheckAuth;
import com.salmon.chatService.common.BaseResponse;
import com.salmon.chatService.common.ResultUtils;
import com.salmon.chatService.model.dto.contact.SearchRequest;
import com.salmon.chatService.model.vo.contact.SearchContactVO;
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

    @Operation(summary = "搜索联系人/群")
    @PostMapping("/search")
    @CheckAuth
    public BaseResponse<SearchContactVO> search(@RequestBody @Valid SearchRequest request) {
        SearchContactVO contactVO = userContactService.search(request);
        return ResultUtils.success(contactVO);
    }

}
