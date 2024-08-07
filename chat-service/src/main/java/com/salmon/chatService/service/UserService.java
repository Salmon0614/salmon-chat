package com.salmon.chatService.service;

import com.salmon.chatService.model.dto.account.EmailLoginRequest;
import com.salmon.chatService.model.dto.account.EmailRegisterRequest;
import com.salmon.chatService.model.dto.account.ForgetPasswordRequest;
import com.salmon.chatService.model.dto.admin.UpdateUserStatusRequest;
import com.salmon.chatService.model.dto.user.UpdatePassword;
import com.salmon.chatService.model.dto.user.UserSaveRequest;
import com.salmon.chatService.model.po.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.salmon.chatService.model.vo.account.TokenUserVo;
import com.salmon.chatService.model.vo.user.UserVO;

import java.util.List;

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
     * 通过邮箱查找用户
     *
     * @param email 邮箱
     * @return 用户信息
     */
    User getOneByEmail(String email);

    /**
     * 通过账号查找用户
     *
     * @param account 账号
     * @return 用户信息
     */
    User getOneByAccount(String account);

    /**
     * 根据邮箱注册
     *
     * @param emailRegisterRequest 邮箱注册信息
     */
    void register(EmailRegisterRequest emailRegisterRequest);

    /**
     * 通过邮箱进行登录
     *
     * @param emailLoginRequest 登录信息
     */
    UserVO login(EmailLoginRequest emailLoginRequest);

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

    /**
     * 用户更新信息
     *
     * @param request 信息请求
     * @return UserVO
     */
    UserVO updateUserInfo(UserSaveRequest request);

    /**
     * 更新密码
     *
     * @param request 请求
     */
    void updatePassword(UpdatePassword request);

    /**
     * 更新用户状态
     *
     * @param request 请求
     */
    void updateUserStatus(UpdateUserStatusRequest request);

    /**
     * 强制用户下线
     *
     * @param userId 用户ID
     */
    void forceOffLine(Integer userId);

    /**
     * 忘记密码
     *
     * @param request 忘记密码请求
     */
    void forgetPassword(ForgetPasswordRequest request);

    /**
     * 批量添加联系人缓存
     *
     * @param account       用户账号
     * @param contactIdList 联系人账号
     */
    void addContactBatch(String account, List<String> contactIdList);

    /**
     * 清空联系人缓存
     *
     * @param account 用户账号
     */
    void cleanContact(String account);

    /**
     * 获取联系人缓存
     *
     * @param account 用户账号
     */
    List<String> getContactList(String account);
}
