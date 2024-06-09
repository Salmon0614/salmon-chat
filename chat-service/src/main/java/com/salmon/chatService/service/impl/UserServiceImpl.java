package com.salmon.chatService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.salmon.chatService.common.ErrorCode;
import com.salmon.chatService.common.StatusEnum;
import com.salmon.chatService.common.UserRoleEnum;
import com.salmon.chatService.config.app.AppConfig;
import com.salmon.chatService.constant.RedisPrefixConstant;
import com.salmon.chatService.constant.Settings;
import com.salmon.chatService.constant.UserConstant;
import com.salmon.chatService.exception.BusinessException;
import com.salmon.chatService.exception.ThrowUtils;
import com.salmon.chatService.model.dto.account.EmailLoginRequest;
import com.salmon.chatService.model.dto.account.EmailRegisterRequest;
import com.salmon.chatService.model.enums.user.AccountBeautyStatusEnum;
import com.salmon.chatService.model.enums.user.UserJoinTypeEnum;
import com.salmon.chatService.model.po.User;
import com.salmon.chatService.mapper.UserMapper;
import com.salmon.chatService.model.po.UserBeauty;
import com.salmon.chatService.model.vo.account.TokenUserVo;
import com.salmon.chatService.model.vo.user.UserVO;
import com.salmon.chatService.service.UserBeautyService;
import com.salmon.chatService.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.salmon.chatService.utils.RedisUtils;
import com.salmon.chatService.utils.Utils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author Salmon
 * @since 2024-06-04
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserBeautyService userBeautyService;

    @Resource
    private AppConfig appConfig;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(EmailRegisterRequest emailRegisterRequest) {
        String email = emailRegisterRequest.getEmail();
        if (this.count(new QueryWrapper<User>().eq("email", email)) > 0) {
            throw new BusinessException("该邮箱已被注册！");
        }
        String account;
        UserBeauty userBeauty = userBeautyService.getOne(new QueryWrapper<UserBeauty>()
                .eq("email", email)
                .eq("status", AccountBeautyStatusEnum.UNUSED.getValue()));
        // 是否有指定靓号
        if (Objects.nonNull(userBeauty)) {
            account = userBeauty.getAccount();
        } else {
            account = Utils.generateAccount();
        }
        String salt = Utils.getSalt();
        int role = UserRoleEnum.USER.getValue();
        if (appConfig.getEmails().contains(email)) {
            role = UserRoleEnum.ADMIN.getValue();
        }
        // 查询是否是指定管理员
        String encryptPassword = Utils.encryptPassword(emailRegisterRequest.getPassword(), salt);
        User user = User.builder()
                .avatar(UserConstant.DEFAULT_AVATAR)
                .gender(UserConstant.DEFAULT_GENDER.getValue())
                .account(account)
                .email(email)
                .role(role)
                .nickname(emailRegisterRequest.getNickname())
                .password(encryptPassword)
                .status(StatusEnum.ENABLE.getValue())
                .joinType(UserJoinTypeEnum.AUTH.getValue())
                .salt(salt).build();
        ThrowUtils.throwIf(!this.save(user), "注册失败");
        userBeauty.setStatus(AccountBeautyStatusEnum.USED.getValue());
        userBeauty.setUserId(user.getId());
        ThrowUtils.throwIf(!userBeautyService.updateById(userBeauty), "注册失败");
        // todo 创建机器人好友
    }

    @Override
    public UserVO login(EmailLoginRequest emailLoginRequest) {
        String email = emailLoginRequest.getEmail();
        String password = emailLoginRequest.getPassword();
        User user = this.getOne(new QueryWrapper<User>().eq("email", email));
        ThrowUtils.throwIf(Objects.isNull(user), ErrorCode.NOT_FOUND_ERROR, "账号或密码不正确！");
        ThrowUtils.throwIf(!user.getPassword().equals(Utils.encryptPassword(password, user.getSalt())), ErrorCode.PARAMS_ERROR, "账号或密码不正确！");
        ThrowUtils.throwIf(user.getStatus() == StatusEnum.DISABLED.getValue(), ErrorCode.FORBIDDEN_ERROR, "您的账号已被禁用！");

        // todo 此账号已经在别处登录
        user.setLastLoginTime(LocalDateTime.now());
        this.updateById(user);
        // 存储 token
        TokenUserVo tokenUserVo = TokenUserVo.objToVo(user);
        String token = Utils.generateToken(user.getAccount());
        this.setUserToken(token, tokenUserVo);

        // todo 查询群、联系人信息等、ws心跳 p-8
        UserVO userVO = UserVO.objToVo(user);
        userVO.setToken(token);
        userVO.setIsAdmin(user.getRole() == UserRoleEnum.ADMIN.getValue());
        return userVO;
    }

    @Override
    public void setUserToken(String token, TokenUserVo tokenUserVo) {
        RedisUtils.set(RedisPrefixConstant.LOGIN_SESSION + token, tokenUserVo, Settings.SESSION_EXPIRE_TIME);
        RedisUtils.set(RedisPrefixConstant.USER_TOKEN + tokenUserVo.getId(), token, Settings.SESSION_EXPIRE_TIME);
    }

    @Override
    public TokenUserVo getUserToken(String token) {
        return RedisUtils.get(RedisPrefixConstant.LOGIN_SESSION + token, TokenUserVo.class);
    }

    @Override
    public TokenUserVo refreshToken(String token) {
        TokenUserVo tokenUserVo = RedisUtils.get(token, TokenUserVo.class);
        ThrowUtils.throwIf(tokenUserVo == null, ErrorCode.NOT_LOGIN_ERROR, "由于您长时间未操作，请重新登录！");
        User user = this.getById(tokenUserVo.getId());
        TokenUserVo newTokenUserVo = TokenUserVo.objToVo(user);
        RedisUtils.set(RedisPrefixConstant.LOGIN_SESSION + token, newTokenUserVo);
        return newTokenUserVo;
    }


}
