package com.salmon.chatService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.salmon.chatService.common.ErrorCode;
import com.salmon.chatService.common.StatusEnum;
import com.salmon.chatService.common.UserRoleEnum;
import com.salmon.chatService.config.app.AppConfig;
import com.salmon.chatService.constant.RedisPrefixConstant;
import com.salmon.chatService.constant.Settings;
import com.salmon.chatService.constant.UserConstant;
import com.salmon.chatService.exception.BusinessException;
import com.salmon.chatService.exception.ThrowUtils;
import com.salmon.chatService.mapper.UserMapper;
import com.salmon.chatService.model.dto.account.EmailLoginRequest;
import com.salmon.chatService.model.dto.account.EmailRegisterRequest;
import com.salmon.chatService.model.dto.account.ForgetPasswordRequest;
import com.salmon.chatService.model.dto.admin.UpdateUserStatusRequest;
import com.salmon.chatService.model.dto.user.UpdatePassword;
import com.salmon.chatService.model.dto.user.UserSaveRequest;
import com.salmon.chatService.model.enums.user.AccountBeautyStatusEnum;
import com.salmon.chatService.model.enums.user.UserJoinTypeEnum;
import com.salmon.chatService.model.enums.userContact.UserContactStatusEnum;
import com.salmon.chatService.model.po.User;
import com.salmon.chatService.model.po.UserBeauty;
import com.salmon.chatService.model.vo.account.TokenUserVo;
import com.salmon.chatService.model.vo.contact.UserContactVO;
import com.salmon.chatService.model.vo.user.UserVO;
import com.salmon.chatService.netty.NettyService;
import com.salmon.chatService.service.UserBeautyService;
import com.salmon.chatService.service.UserContactService;
import com.salmon.chatService.service.UserService;
import com.salmon.chatService.utils.RedisUtils;
import com.salmon.chatService.utils.UserHolder;
import com.salmon.chatService.utils.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    @Lazy
    private UserContactService userContactService;
    @Resource
    private NettyService nettyService;
    @Resource
    private AppConfig appConfig;


    @Override
    public User getOneByEmail(String email) {
        return this.getOne(new QueryWrapper<User>().eq("email", email));
    }

    @Override
    public User getOneByAccount(String account) {
        return this.getOne(new QueryWrapper<User>().eq("account", account));
    }

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
        String encryptPassword = Utils.encryptPassword(emailRegisterRequest.getPassword(), salt);
        // 查询是否是指定管理员
        int role = UserRoleEnum.USER.getValue();
        if (appConfig.getEmails().contains(email)) {
            role = UserRoleEnum.ADMIN.getValue();
        }
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
        if (Objects.nonNull(userBeauty)) {
            userBeauty.setStatus(AccountBeautyStatusEnum.USED.getValue());
            userBeauty.setUserId(user.getId());
            ThrowUtils.throwIf(!userBeautyService.updateById(userBeauty), "注册失败");
        }
        // 创建机器人好友
        userContactService.addRobotContact(user.getId(), user.getAccount());
    }

    @Override
    public UserVO login(EmailLoginRequest emailLoginRequest) {
        String email = emailLoginRequest.getEmail();
        String password = emailLoginRequest.getPassword();

        // 验证信息是否合法
        User user = this.getOneByEmail(email);
        ThrowUtils.throwIf(Objects.isNull(user), ErrorCode.NOT_FOUND_ERROR, "账号或密码不正确！");
        ThrowUtils.throwIf(!user.getPassword().equals(Utils.encryptPassword(password, user.getSalt())), ErrorCode.PARAMS_ERROR, "账号或密码不正确！");
        ThrowUtils.throwIf(user.getStatus() == StatusEnum.DISABLED.getValue(), ErrorCode.FORBIDDEN_ERROR, "您的账号已被禁用！");

        // 限制单设备登录
        Long userHeartBeat = nettyService.getUserHeartBeat(user.getAccount());
        ThrowUtils.throwIf(Objects.nonNull(userHeartBeat), "此账号已在别处登录，请退出后再登录");

        // 存储登录token
        TokenUserVo tokenUserVo = TokenUserVo.objToVo(user);
        String token = Utils.generateToken(user.getAccount());
        this.setUserToken(token, tokenUserVo);

        // todo 查询群、联系人信息等、ws心跳 p-8
        // 查询联系人
        List<UserContactVO> userContactVOS = userContactService.selectUserContact(user.getId(), UserContactStatusEnum.FRIEND.getValue());
        List<String> accountList = userContactVOS.stream().map(UserContactVO::getContactAccount).toList();
        cleanContact(user.getAccount());
        if (!CollectionUtils.isEmpty(accountList)) {
            addContactBatch(user.getAccount(), accountList);
        }


        UserVO userVO = UserVO.objToVo(user);
        userVO.setToken(token);
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

    /**
     * 用户更新信息
     *
     * @param request 信息请求
     * @return UserVO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserVO updateUserInfo(UserSaveRequest request) {
        TokenUserVo tokenUserVo = UserHolder.getUser();
        User user = this.getById(tokenUserVo.getId());
        String dbNickname = user.getNickname();
        BeanUtils.copyProperties(request, user);
        ThrowUtils.throwIf(!this.updateById(user), ErrorCode.OPERATION_ERROR);
        if (!dbNickname.equals(request.getNickname())) {
            // todo 更新会话信息中的昵称信息
        }
        return UserVO.objToVo(user);
    }

    @Override
    public void updatePassword(UpdatePassword request) {
        TokenUserVo tokenUserVo = UserHolder.getUser();
        User user = this.getById(tokenUserVo.getId());
        String encryptPassword = Utils.encryptPassword(request.getPassword(), user.getSalt());
        user.setPassword(encryptPassword);
        ThrowUtils.throwIf(!this.updateById(user), ErrorCode.OPERATION_ERROR);
        // todo 强制退出，重新登录
    }

    @Override
    public void updateUserStatus(UpdateUserStatusRequest request) {
        StatusEnum statusEnum = StatusEnum.getEnumByValue(request.getStatus());
        ThrowUtils.throwIf(Objects.isNull(statusEnum), ErrorCode.PARAMS_ERROR);
        User user = this.getById(request.getUserId());
        user.setStatus(request.getStatus());
        ThrowUtils.throwIf(!this.updateById(user), ErrorCode.OPERATION_ERROR);
    }

    @Override
    public void forceOffLine(Integer userId) {
        // todo 强制下线
    }

    @Override
    public void forgetPassword(ForgetPasswordRequest request) {
        User user = this.getOne(new LambdaQueryWrapper<User>().eq(User::getEmail, request.getEmail()));
        String encryptPassword = Utils.encryptPassword(request.getPassword(), user.getSalt());
        user.setPassword(encryptPassword);
        ThrowUtils.throwIf(!this.updateById(user), ErrorCode.OPERATION_ERROR);
        // todo 强制退出，重新登录
    }

    @Override
    public void addContactBatch(String account, List<String> contactIdList) {
        RedisUtils.lSet1(RedisPrefixConstant.USER_CONTACT + account, contactIdList, Settings.SESSION_EXPIRE_TIME);
    }

    @Override
    public void cleanContact(String account) {
        RedisUtils.del(RedisPrefixConstant.USER_CONTACT + account);
    }

    @Override
    public List<String> getContactList(String account) {
        return RedisUtils.lGet(RedisPrefixConstant.USER_CONTACT + account, 0, -1);
    }


}
