package com.salmon.chatService.service.impl;

import cn.hutool.core.util.ObjectUtil;
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
import com.salmon.chatService.model.vo.contact.ApplyResultVO;
import com.salmon.chatService.model.vo.contact.SearchContactVO;
import com.salmon.chatService.model.vo.contact.UserContactVO;
import com.salmon.chatService.service.GroupService;
import com.salmon.chatService.service.UserContactApplyService;
import com.salmon.chatService.service.UserContactService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.salmon.chatService.service.UserService;
import com.salmon.chatService.utils.UserHolder;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
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
        String account = request.getContactAccount();
        UserContactTypeEnum contactTypeEnum = UserContactTypeEnum.getByPrefix(account);
        if (Objects.isNull(contactTypeEnum)) {
            return null;
        }
        SearchContactVO searchContactVO = new SearchContactVO();
        switch (contactTypeEnum) {
            case USER -> {
                User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getAccount, account));
                if (Objects.isNull(user)) {
                    return null;
                }
                searchContactVO.setName(user.getNickname());
                searchContactVO.setAvatar(user.getAvatar());
                searchContactVO.setGender(user.getGenderDesc());
                searchContactVO.setArea(user.getArea());
                searchContactVO.setId(user.getId());
            }
            case GROUP -> {
                Group group = groupService.getOne(new LambdaQueryWrapper<Group>()
                        .eq(Group::getGroupNumber, account)
                        .eq(Group::getStatus, GroupStatusEnum.NORMAL.getValue())
                );
                if (Objects.isNull(group)) {
                    return null;
                }
                searchContactVO.setName(group.getGroupName());
                searchContactVO.setAvatar(group.getGroupCover());
                searchContactVO.setId(group.getId());
            }
        }
        searchContactVO.setContactType(contactTypeEnum.getType());
        searchContactVO.setAccount(account);

        // 搜出来的是自己
        if (tokenUserVo.getAccount().equals(account)) {
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
        if (applyOriginTypeEnum.getValue() == ApplyOriginTypeEnum.ACCOUNT.getValue()) {
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
        // 先查询该好友是否已经添加，如果他拉黑了这个用户，则不能添加
        UserContact userContact = this.selectContact(applyUserId, contactId, contactTypeEnum.getType());
        ThrowUtils.throwIf(
                Objects.nonNull(userContact) && userContact.getStatus().equals(UserContactStatusEnum.BE_BLACK.getValue()),
                "对方已把你拉黑，无法添加"
        );
        // 如果已经是好友，不做处理
        if (userContact.getStatus().equals(UserContactStatusEnum.FRIEND.getValue())) {
            return ApplyResultVO.builder().joinType(joinType).build();
        }
        // 如果无需审核，直接加入
        if (joinType.equals(JoinTypeEnum.JOIN.getValue())) {
            // todo 添加联系人
            return ApplyResultVO.builder().joinType(joinType).build();
        }
        // 默认申请信息
        String applyInfo = request.getApplyInfo();
        if (!StringUtils.hasText(applyInfo)) {
            applyInfo = String.format(CommonConstant.applyInfoForUser, tokenUserVo.getNickname());
            // 添加方式是从群成员列表添加的
            if (request.getApplyType() == ApplyTypeEnum.GROUP.getValue()) {
                // 如果通过群聊添加好友，那枚举是群就不合法
                ThrowUtils.throwIf(contactTypeEnum.getType() == UserContactTypeEnum.GROUP.getType(), ErrorCode.PARAMS_ERROR);
                Group group = groupService.getById(request.getGroupId());
                // 群不存在或者已经解散
                ThrowUtils.throwIf(Objects.isNull(group) || group.getStatus().equals(GroupStatusEnum.DISSOLUTION.getValue()), ErrorCode.PARAMS_ERROR);
                applyInfo = String.format(CommonConstant.applyInfoForGroup, group.getGroupName(), tokenUserVo.getNickname());
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
}
