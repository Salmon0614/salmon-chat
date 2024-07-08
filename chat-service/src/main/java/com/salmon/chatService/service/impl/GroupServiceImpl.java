package com.salmon.chatService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.salmon.chatService.common.ErrorCode;
import com.salmon.chatService.common.IdRequest;
import com.salmon.chatService.constant.Settings;
import com.salmon.chatService.exception.BusinessException;
import com.salmon.chatService.exception.ThrowUtils;
import com.salmon.chatService.mapper.GroupMapper;
import com.salmon.chatService.mapper.GroupVOMapper;
import com.salmon.chatService.model.dto.group.GroupQueryRequest;
import com.salmon.chatService.model.enums.contact.UserContactTypeEnum;
import com.salmon.chatService.model.enums.group.GroupStatusEnum;
import com.salmon.chatService.model.enums.userContact.UserContactStatusEnum;
import com.salmon.chatService.model.po.Group;
import com.salmon.chatService.model.po.UserContact;
import com.salmon.chatService.model.vo.app.SystemConfigVo;
import com.salmon.chatService.model.vo.contact.UserContactVO;
import com.salmon.chatService.model.vo.group.GroupChatVO;
import com.salmon.chatService.model.vo.group.GroupVO;
import com.salmon.chatService.service.AppService;
import com.salmon.chatService.service.GroupService;
import com.salmon.chatService.service.UserContactService;
import com.salmon.chatService.utils.UserHolder;
import com.salmon.chatService.utils.Utils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 群聊 服务实现类
 * </p>
 *
 * @author Salmon
 * @since 2024-06-08
 */
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, Group> implements GroupService {

    @Resource
    private AppService appService;
    @Resource
    private UserContactService userContactService;
    @Resource
    private GroupVOMapper groupVOMapper;

    /**
     * 用户创建或修改群组
     *
     * @param group 群聊
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdateGroup(Group group) {
        // 如果是新增，查看创建的群数量是否达到限制数，以及生成群号
        if (Objects.isNull(group.getId())) {
            SystemConfigVo systemConfig = appService.getSystemConfig();
            Integer maxGroupCount = systemConfig.getMaxGroupCount();
            long count = this.count(new LambdaQueryWrapper<Group>()
                    .eq(Group::getGroupOwnerId, group.getGroupOwnerId())
                    .eq(Group::getStatus, GroupStatusEnum.NORMAL.getValue())
            );
            ThrowUtils.throwIf(count >= maxGroupCount, "最多只能创建" + maxGroupCount + "个群组");
            String groupNumber = Utils.generateGroupNumber();
            group.setGroupNumber(groupNumber);
            if (!StringUtils.hasText(group.getGroupCover())) {
                group.setGroupCover(Settings.DEFAULT_GROUP_COVER);
            }
            ThrowUtils.throwIf(!this.save(group), ErrorCode.OPERATION_ERROR, "创建失败");

            // 将群组添加为联系人
            UserContact userContact = new UserContact();
            userContact.setContactType(UserContactTypeEnum.GROUP.getType());
            userContact.setStatus(UserContactStatusEnum.FRIEND.getValue());
            userContact.setContactId(group.getId());
            userContact.setUserId(group.getGroupOwnerId());
            userContactService.save(userContact);

            // todo 创建会话
            // todo ws 发送消息
        } else {
            Group oldGroup = this.getById(group.getId());
            // 操作的用户不是群主，直接拒绝
            ThrowUtils.throwIf(!oldGroup.getGroupOwnerId().equals(group.getGroupOwnerId()), ErrorCode.PARAMS_ERROR);
            ThrowUtils.throwIf(!this.updateById(group), ErrorCode.OPERATION_ERROR, "保存失败");
            // todo 更新相关表冗余信息

            // todo 修改群昵称发送ws消息
        }
        // todo 封面图片 路径 + 群组ID 区分 原图，缩略图
    }

    /**
     * 获取群组信息
     *
     * @param request 请求
     * @return GroupVO
     */
    @Override
    public GroupVO getGroupInfo(IdRequest request) {
        Long groupId = request.getId();
        Group group = this.getGroupDetail(groupId.intValue());
        GroupVO groupVO = GroupVO.objToVo(group);
        // 查询群成员数
        long count = userContactService.count(
                new LambdaQueryWrapper<UserContact>()
                        .eq(UserContact::getContactId, groupId)
                        .eq(UserContact::getContactType, UserContactTypeEnum.GROUP.getType())
                        .eq(UserContact::getStatus, UserContactStatusEnum.FRIEND.getValue())
        );
        groupVO.setMemberCount((int) count);
        return groupVO;
    }

    /**
     * 查询群组详情
     *
     * @param groupId 群ID
     * @return Group
     */
    @Override
    public Group getGroupDetail(Integer groupId) {
        Group group = this.getById(groupId);
        ThrowUtils.throwIf(
                Objects.isNull(group) || !group.getStatus().equals(GroupStatusEnum.NORMAL.getValue()),
                "群聊不存在或已解散"
        );
        Integer userId = UserHolder.getUser().getId();
        // 查询用户是否已经在群聊中
        UserContact userContact = userContactService.getOne(new LambdaQueryWrapper<UserContact>()
                .eq(UserContact::getContactId, groupId)
                .eq(UserContact::getUserId, userId)
        );
        ThrowUtils.throwIf(
                Objects.isNull(userContact) || !userContact.getStatus().equals(UserContactStatusEnum.FRIEND.getValue()),
                "你不在群聊中");
        return group;
    }

    /**
     * 获取群聊详细信息（包括成员）
     *
     * @param request ID请求
     * @return GroupVO
     */
    @Override
    public GroupChatVO getGroupInfo4Chat(IdRequest request) {
        Long groupId = request.getId();
        Group group = this.getGroupDetail(groupId.intValue());
        GroupVO groupVO = GroupVO.objToVo(group);
        List<UserContactVO> userContactVOS = userContactService.selectContactUserInfo(groupId, UserContactTypeEnum.GROUP.getType(), UserContactStatusEnum.FRIEND.getValue());
        if (CollectionUtils.isEmpty(userContactVOS)) {
            userContactVOS = new ArrayList<>();
        }
        groupVO.setMemberCount(userContactVOS.size());
        GroupChatVO groupChatVO = new GroupChatVO();
        groupChatVO.setGroupVO(groupVO);
        groupChatVO.setUserContactVOS(userContactVOS);
        return groupChatVO;
    }

    /**
     * 后台分页查询群聊
     *
     * @param request 查询请求
     * @return 分页对象
     */
    @Override
    public Page<GroupVO> queryGroupVOPage(GroupQueryRequest request) {
        long current = request.getCurrent();
        long size = request.getPageSize();
        LambdaQueryWrapper<Group> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(request.getGroupName())) {
            queryWrapper.eq(Group::getGroupName, request.getGroupName());
        }
        if (StringUtils.hasText(request.getGroupOwnerAccount())) {
            queryWrapper.eq(Group::getGroupOwnerAccount, request.getGroupOwnerAccount());
        }
        if (StringUtils.hasText(request.getGroupNumber())) {
            queryWrapper.eq(Group::getGroupNumber, request.getGroupNumber());
        }
        queryWrapper.orderByDesc(Group::getCreateTime);
        return groupVOMapper.page(new Page<>(current, size), queryWrapper);
    }

    /**
     * 解散群聊
     *
     * @param groupOwnerId 群主id
     * @param groupId      群id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void dissolutionGroup(Integer groupOwnerId, Integer groupId) {
        Group group = this.getById(groupId);
        if (Objects.isNull(group) || !group.getGroupOwnerId().equals(groupOwnerId)) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        // 解散群聊
        group.setStatus(GroupStatusEnum.DISSOLUTION.getValue());
        ThrowUtils.throwIf(!this.updateById(group), ErrorCode.OPERATION_ERROR, "解散失败");
        // 更新联系人信息
        LambdaUpdateWrapper<UserContact> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(UserContact::getContactId, groupId);
        updateWrapper.eq(UserContact::getContactType, UserContactTypeEnum.GROUP.getType());
        updateWrapper.set(UserContact::getStatus, UserContactStatusEnum.DEL.getValue());
        ThrowUtils.throwIf(!userContactService.update(updateWrapper), ErrorCode.OPERATION_ERROR, "解散失败");

        // todo 移除相关群员的联系人缓存


        // todo 发消息 1.更新会话消息 2.记录群消息 3.发送解散通知消息
    }
}
