package com.salmon.chatService.service;

import com.salmon.chatService.model.dto.account.EmailLogin;
import com.salmon.chatService.model.dto.account.EmailRegister;
import com.salmon.chatService.model.po.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.salmon.chatService.model.vo.account.TokenUserVo;
import com.salmon.chatService.model.vo.user.UserVO;

/**
 * <p>
 * 用户 服务类
 * </p>
 *
 * @author Salmon
 * @since 2024-06-04
 */
public interface UserService extends IService<User> {

    /**
     * 根据邮箱注册
     *
     * @param emailRegister 邮箱注册信息
     */
    void register(EmailRegister emailRegister);

    /**
     * 通过邮箱进行登录
     *
     * @param emailLogin 登录信息
     */
    UserVO login(EmailLogin emailLogin);

    /**
     * 设置token
     *
     * @param token       token
     * @param tokenUserVo 会话信息
     */
    void setUserToken(String token, TokenUserVo tokenUserVo);

    /**
     * 获取token
     *
     * @param token token
     */
    TokenUserVo getUserToken(String token);

    /**
     * 刷新token
     *
     * @return 新的登录信息
     */
    TokenUserVo refreshToken(String token);
}
