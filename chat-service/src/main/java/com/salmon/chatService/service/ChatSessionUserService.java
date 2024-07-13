package com.salmon.chatService.service;

import com.salmon.chatService.model.po.ChatSessionUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.salmon.chatService.model.vo.chatSessionUser.ChatSessionUserVO;

import java.util.List;

/**
 * <p>
 * 聊天会话用户 服务类
 * </p>
 *
 * @author Salmon
 * @since 2024-07-12
 */
public interface ChatSessionUserService extends IService<ChatSessionUser> {

    /**
     * 查询该账号的所有会话
     *
     * @param account 用户账号
     * @return 用户会话
     */
    List<ChatSessionUserVO> selectSessionUserList(String account);
}
