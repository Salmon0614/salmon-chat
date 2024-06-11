package com.salmon.chatService.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.salmon.chatService.common.ErrorCode;
import com.salmon.chatService.common.JoinTypeEnum;
import com.salmon.chatService.constant.CommonConstant;
import com.salmon.chatService.exception.ThrowUtils;
import com.salmon.chatService.model.dto.contact.ApplyRequest;
import com.salmon.chatService.model.dto.contact.SearchRequest;
import com.salmon.chatService.model.enums.contact.ApplyOriginTypeEnum;
import com.salmon.chatService.model.enums.contact.ApplyTypeEnum;
import com.salmon.chatService.model.enums.contact.ContactApplyStatusEnum;
import com.salmon.chatService.model.enums.contact.UserContactTypeEnum;
import com.salmon.chatService.model.enums.group.GroupStatusEnum;
import com.salmon.chatService.model.enums.userContact.UserContactStatusEnum;
import com.salmon.chatService.model.po.Group;
import com.salmon.chatService.model.po.User;
import com.salmon.chatService.model.po.UserContact;
import com.salmon.chatService.mapper.UserContactMapper;
import com.salmon.chatService.model.po.UserContactApply;
import com.salmon.chatService.model.vo.account.TokenUserVo;
import com.salmon.chatService.model.vo.app.SystemConfigVo;
import com.salmon.chatService.model.vo.contact.ApplyResultVO;
import com.salmon.chatService.model.vo.contact.ContactInfoVO;
import com.salmon.chatService.model.vo.contact.SearchContactVO;
import com.salmon.chatService.model.vo.contact.UserContactVO;
import com.salmon.chatService.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.salmon.chatService.utils.UserHolder;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 联系人 服务实现类
 * </p>
 *
 * @author Salmon
 * @since 2024-06-08
 */
@Service
public class UserContactServiceImpl extends ServiceImpl<UserContactMapper, UserContact> implements UserContactService {

    @Resource
    private UserService userService;
    @Resource
    @Lazy
    private GroupService groupService;
    @Resource
    private UserContactApplyService userContactApplyService;
    @Resource
    private AppService appService;

    /**
     * 查询联系人信息
     *
     * @param contactId   联系人ID
     * @param contactType 联系人类型
     * @param status      关系状态
     */
    @Override
    public List<UserContactVO> selectContactUserInfo(Long contactId, Integer contactType, Integer status) {
        return this.baseMapper.selectContactUserInfo(contactId, contactType, status);
    }

    /**
     * 搜索联系人
     *
     * @param request 搜索请求
     * @return SearchContactVO
     */
    @Override
    public SearchContactVO search(SearchRequest request) {
        TokenUserVo tokenUserVo = UserHolder.getUser();
        String keyword = request.getKeyword();
        ApplyOriginTypeEnum applyOriginTypeEnum = ApplyOriginTypeEnum.validType(keyword);
        UserContactTypeEnum contactTypeEnum = UserContactTypeEnum.getByPrefix(keyword);
        if (applyOriginTypeEnum != ApplyOriginTypeEnum.ACCOUNT) {
            contactTypeEnum = UserContactTypeEnum.USER;
        }
        if (Objects.isNull(contactTypeEnum)) {
            return null;
        }
        SearchContactVO searchContactVO = new SearchContactVO();
        switch (contactTypeEnum) {
            case USER -> {
                LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
                switch (applyOriginTypeEnum) {
                    case ACCOUNT -> {
                        queryWrapper.eq(User::getAccount, keyword);
                    }
                    case EMAIL -> {
                        queryWrapper.eq(User::getEmail, keyword);
                    }
                    case MOBILE -> {
                        queryWrapper.eq(User::getMobile, keyword);
                    }
                }
                User user = userService.getOne(queryWrapper);
                if (Objects.isNull(user)) {
                    return null;
                }
                searchContactVO.setAccount(user.getAccount());
                searchContactVO.setName(user.getNickname());
                searchContactVO.setAvatar(user.getAvatar());
                searchContactVO.setGender(user.getGender());
                searchContactVO.setArea(user.getArea());
                searchContactVO.setId(user.getId());
            }
            case GROUP -> {
                Group group = groupService.getOne(new LambdaQueryWrapper<Group>()
                        .eq(Group::getGroupNumber, keyword)
                        .eq(Group::getStatus, GroupStatusEnum.NORMAL.getValue())
                );
                if (Objects.isNull(group)) {
                    return null;
                }
                searchContactVO.setName(group.getGroupName());
                searchContactVO.setAvatar(group.getGroupCover());
                searchContactVO.setId(group.getId());
                searchContactVO.setAccount(group.getGroupNumber());
            }
        }
        searchContactVO.setContactType(contactTypeEnum.getType());
        // 搜出来的是自己
        if (tokenUserVo.getAccount().equals(keyword)) {
            searchContactVO.setStatus(UserContactStatusEnum.FRIEND.getValue());
            return searchContactVO;
        }
        // 查询是否是好友
        UserContact userContact = this.selectContact(tokenUserVo.getId(), searchContactVO.getId(), searchContactVO.getContactType());
        if (Objects.nonNull(userContact)) {
            searchContactVO.setStatus(userContact.getStatus());
        }
        return searchContactVO;
    }

    /**
     * 申请加入好友/群聊
     *
     * @param request 申请加入请求
     * @return ApplyResultVO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApplyResultVO applyAdd(ApplyRequest request) {
        String account = request.getContactAccount();
        ApplyOriginTypeEnum applyOriginTypeEnum = ApplyOriginTypeEnum.validType(account);
        UserContactTypeEnum contactTypeEnum = UserContactTypeEnum.getByPrefix(account);
        if (applyOriginTypeEnum == ApplyOriginTypeEnum.ACCOUNT) {
            ThrowUtils.throwIf(Objects.isNull(contactTypeEnum), ErrorCode.PARAMS_ERROR);
        } else {
            contactTypeEnum = UserContactTypeEnum.USER;
        }
        TokenUserVo tokenUserVo = UserHolder.getUser();
        Integer joinType = null;
        Integer contactId = null;
        Integer receiveUserId = null;
        int originType = applyOriginTypeEnum.getValue();
        switch (contactTypeEnum) {
            case USER -> {
                LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
                switch (applyOriginTypeEnum) {
                    case ACCOUNT -> {
                        queryWrapper.eq(User::getAccount, account);
                    }
                    case EMAIL -> {
                        queryWrapper.eq(User::getEmail, account);
                    }
                    case MOBILE -> {
                        queryWrapper.eq(User::getMobile, account);
                    }
                }
                User user = userService.getOne(queryWrapper);
                ThrowUtils.throwIf(Objects.isNull(user), "该用户不存在");
                joinType = user.getJoinType();
                contactId = user.getId();
                receiveUserId = user.getId();
            }
            case GROUP -> {
                Group group = groupService.getOne(new LambdaQueryWrapper<Group>()
                        .eq(Group::getGroupNumber, account)
                        .eq(Group::getStatus, GroupStatusEnum.NORMAL.getValue())
                );
                ThrowUtils.throwIf(Objects.isNull(group), "该群聊不存在或已解散");
                joinType = group.getJoinType();
                contactId = group.getId();
                receiveUserId = group.getGroupOwnerId(); // 群主接收申请信息
            }
        }
        // 这些参数有空值，直接抛
        ThrowUtils.throwIf(ObjectUtil.hasEmpty(joinType, contactId, receiveUserId), ErrorCode.PARAMS_ERROR);
        Integer applyUserId = tokenUserVo.getId();
        // 先查询该好友是否已经添加，如果拉黑了这个用户，则不能添加
        UserContact userContact = this.selectContact(applyUserId, contactId, contactTypeEnum.getType());
        ThrowUtils.throwIf(
                Objects.nonNull(userContact) &&
                        Arrays.asList(
                                UserContactStatusEnum.BE_BLACK.getValue(),
                                UserContactStatusEnum.FIRST_BE_BLACK.getValue()
                        ).contains(userContact.getStatus()),
                "对方已把你拉黑，无法添加"
        );
        // 如果已经是好友，不做处理
        if (userContact.getStatus().equals(UserContactStatusEnum.FRIEND.getValue())) {
            return ApplyResultVO.builder().joinType(joinType).build();
        }
        // 如果无需审核，直接加入
        if (joinType.equals(JoinTypeEnum.JOIN.getValue())) {
            addContact(applyUserId, contactId, receiveUserId, contactTypeEnum.getType());
            return ApplyResultVO.builder().joinType(joinType).build();
        }
        // 默认申请信息
        String applyInfo = request.getApplyInfo();
        if (!StringUtils.hasText(applyInfo)) {
            applyInfo = String.format(CommonConstant.APPLY_INFO_FOR_USER, tokenUserVo.getNickname());
            // 添加方式是从群成员列表添加的
            if (request.getApplyType() == ApplyTypeEnum.GROUP.getValue()) {
                // 如果通过群聊添加好友，那枚举是群就不合法
                ThrowUtils.throwIf(contactTypeEnum == UserContactTypeEnum.GROUP, ErrorCode.PARAMS_ERROR);
                Group group = groupService.getById(request.getGroupId());
                // 群不存在或者已经解散
                ThrowUtils.throwIf(Objects.isNull(group) || group.getStatus().equals(GroupStatusEnum.DISSOLUTION.getValue()), ErrorCode.PARAMS_ERROR);
                applyInfo = String.format(CommonConstant.APPLY_INFO_FOR_GROUP, group.getGroupName(), tokenUserVo.getNickname());
                originType = ApplyOriginTypeEnum.GROUP.getValue();
            }
        }
        // 封装申请记录信息
        UserContactApply userContactApply = userContactApplyService.getOne(
                new LambdaQueryWrapper<UserContactApply>()
                        .eq(UserContactApply::getApplyUserId, applyUserId)
                        .eq(UserContactApply::getReceiveUserId, receiveUserId)
                        .eq(UserContactApply::getContactType, contactTypeEnum.getType())
        );
        boolean isSendWs = true;
        if (Objects.isNull(userContactApply)) {
            userContactApply = new UserContactApply();
            userContactApply.setApplyUserId(applyUserId);
            userContactApply.setContactId(contactId);
            userContactApply.setReceiveUserId(receiveUserId);
            userContactApply.setContactType(contactTypeEnum.getType());
            userContactApply.setApplyInfo(applyInfo);
            userContactApply.setOriginType(originType);
            userContactApply.setStatus(ContactApplyStatusEnum.WAIT.getValue());
            userContactApply.setLastApplyTime(LocalDateTime.now());
            ThrowUtils.throwIf(!userContactApplyService.save(userContactApply), "申请失败，请刷新重试");
        } else {
            // 判断原来的状态是否是待处理，如果不是，说明该用户已经处理过之前的申请了，则这次申请得重新通知一次给该用户
            isSendWs = !userContactApply.getStatus().equals(ContactApplyStatusEnum.WAIT.getValue());
            // 更新申请记录（可能对方已删除，重新添加）
            userContactApply.setStatus(ContactApplyStatusEnum.WAIT.getValue());
            userContactApply.setLastApplyTime(LocalDateTime.now());
            userContactApply.setOriginType(originType);
            userContactApply.setApplyInfo(applyInfo);
            ThrowUtils.throwIf(!userContactApplyService.updateById(userContactApply), "申请失败，请刷新重试");
        }
        if (isSendWs) {
            // todo 发送 ws 消息，让用户收到申请消息
        }
        return ApplyResultVO.builder().joinType(joinType).build();
    }

    /**
     * 查询联系人关系
     *
     * @param userId      用户ID
     * @param contactId   联系人ID
     * @param contactType 联系人类型
     * @return UserContact
     */
    @Override
    public UserContact selectContact(Integer userId, Integer contactId, Integer contactType) {
        // 查询是否是好友
        return this.getOne(new LambdaQueryWrapper<UserContact>()
                .eq(UserContact::getUserId, userId)
                .eq(UserContact::getContactId, contactId)
                .eq(UserContact::getContactType, contactType)
        );
    }

    /**
     * 添加联系人
     *
     * @param userId        用户ID
     * @param contactId     联系人ID/群ID
     * @param receiveUserId 联系人ID/群管理员ID
     * @param contactType   联系类型
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addContact(Integer userId, Integer contactId, Integer receiveUserId, Integer contactType) {
        UserContactTypeEnum contactTypeEnum = UserContactTypeEnum.getEnumByValue(contactType);
        ThrowUtils.throwIf(Objects.isNull(contactTypeEnum), ErrorCode.PARAMS_ERROR);
        if (contactTypeEnum == UserContactTypeEnum.GROUP) {
            // 校验人数是否已满
            long count = this.count(new LambdaQueryWrapper<UserContact>()
                    .eq(UserContact::getContactId, contactId)
                    .eq(UserContact::getContactType, contactType)
                    .eq(UserContact::getStatus, UserContactStatusEnum.FRIEND.getValue())
            );
            SystemConfigVo systemConfig = appService.getSystemConfig();
            ThrowUtils.throwIf(count >= systemConfig.getMaxGroupCount(), "该群聊成员已满，无法加入");
        }
        List<UserContact> userContacts = new ArrayList<>();
        // 申请人添加对方
        UserContact addContact = this.selectContact(userId, contactId, contactType);
        if (Objects.isNull(addContact)) {
            addContact = new UserContact();
            addContact.setUserId(userId);
            addContact.setContactType(contactType);
            addContact.setContactId(contactId);
        }
        addContact.setStatus(UserContactStatusEnum.FRIEND.getValue());
        userContacts.add(addContact);
        // 接收人添加申请人（群的话不需要）
        if (contactTypeEnum == UserContactTypeEnum.USER) {
            UserContact beAddContact = new UserContact();
            beAddContact.setUserId(contactId);
            beAddContact.setContactType(contactType);
            beAddContact.setContactId(userId);
            beAddContact.setStatus(UserContactStatusEnum.FRIEND.getValue());
            userContacts.add(beAddContact);
        }
        ThrowUtils.throwIf(!this.saveOrUpdateBatch(userContacts), "添加失败");
        // todo 发送ws 添加缓存

        // todo 创建会话，发送消息
    }

    /**
     * 加载联系人列表
     *
     * @param contactType 类型
     * @return List<UserContactVO>
     */
    @Override
    public List<UserContactVO> loadContact(Integer contactType) {
        TokenUserVo tokenUserVo = UserHolder.getUser();
        String statusArr = "(" + StrUtil.join(
                ",",
                UserContactStatusEnum.FRIEND.getValue(),
                UserContactStatusEnum.BE_DEL.getValue(),
                UserContactStatusEnum.BE_BLACK.getValue()
        ) + ")";
        if (contactType == UserContactTypeEnum.USER.getType()) {
            return this.baseMapper.selectContactUserInfoList(tokenUserVo.getId(), contactType, statusArr);
        } else {
            // 排除自己创建的群
            return this.baseMapper.selectContactGroupInfoList(tokenUserVo.getId(), contactType, statusArr);
        }
    }

    /**
     * 获取联系人详情（不一定是好友，比如群成员里看详情））
     *
     * @param id 联系人ID
     * @return ContactInfoVO
     */
    @Override
    public ContactInfoVO getContactInfo(Long id) {
        User user = userService.getById(id);
        ThrowUtils.throwIf(Objects.isNull(user), ErrorCode.PARAMS_ERROR);
        ContactInfoVO contactInfoVO = ContactInfoVO.objToVo(user);
        contactInfoVO.setStatus(UserContactStatusEnum.NOT_FRIEND.getValue());
        TokenUserVo tokenUserVo = UserHolder.getUser();
        UserContact userContact = this.selectContact(tokenUserVo.getId(), id.intValue(), UserContactTypeEnum.USER.getType());
        // 判断是否是好友
        if (Objects.nonNull(userContact)) {
            contactInfoVO.setStatus(userContact.getStatus());
        }
        return contactInfoVO;
    }

    /**
     * 获取联系人详情（必须是好友（包含被拉黑或者被删））
     *
     * @param id 联系人ID
     * @return ContactInfoVO
     */
    @Override
    public ContactInfoVO getContactUserInfo(Long id) {
        TokenUserVo tokenUserVo = UserHolder.getUser();
        UserContact userContact = this.selectContact(tokenUserVo.getId(), id.intValue(), UserContactTypeEnum.USER.getType());
        ThrowUtils.throwIf(
                Objects.isNull(userContact)
                        || !Arrays.asList(
                        UserContactStatusEnum.FRIEND.getValue(),
                        UserContactStatusEnum.BE_DEL.getValue(),
                        UserContactStatusEnum.BE_BLACK.getValue()
                ).contains(userContact.getStatus())
                , ErrorCode.PARAMS_ERROR
        );
        User user = userService.getById(id);
        ThrowUtils.throwIf(Objects.isNull(user), ErrorCode.PARAMS_ERROR);
        ContactInfoVO contactInfoVO = ContactInfoVO.objToVo(user);
        contactInfoVO.setStatus(userContact.getStatus());
        return contactInfoVO;
    }

    /**
     * 移除联系人（删除或者拉黑）
     *
     * @param contactId         联系人ID
     * @param contactStatusEnum 状态枚举
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeUserContact(int contactId, UserContactStatusEnum contactStatusEnum) {
        TokenUserVo tokenUserVo = UserHolder.getUser();
        // 移除好友
        UserContact myContact = this.selectContact(tokenUserVo.getId(), contactId, UserContactTypeEnum.USER.getType());
        ThrowUtils.throwIf(Objects.isNull(myContact), ErrorCode.PARAMS_ERROR);
        myContact.setStatus(contactStatusEnum.getValue());
        ThrowUtils.throwIf(!this.updateById(myContact), "操作失败");
        // 将好友中也移除自己
        UserContact friendContact = this.selectContact(contactId, tokenUserVo.getId(), UserContactTypeEnum.USER.getType());
        if (contactStatusEnum == UserContactStatusEnum.DEL) {
            friendContact.setStatus(UserContactStatusEnum.BE_DEL.getValue());
        } else if (contactStatusEnum == UserContactStatusEnum.BLACK) {
            friendContact.setStatus(UserContactStatusEnum.BE_BLACK.getValue());
        }
        ThrowUtils.throwIf(!this.updateById(friendContact), "操作失败");
        // todo 从我的好友缓存列表删除好友

        // todo 从好友的缓存列表删除我
    }
}
