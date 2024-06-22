package com.salmon.chatService.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 聊天应用
 *
 * @author Salmon
 * @since 2024-06-21
 */
@RestController
@RequestMapping("/chat")
@Slf4j
@Tag(name = "ChatController", description = "聊天相关API")
public class ChatController {

}
