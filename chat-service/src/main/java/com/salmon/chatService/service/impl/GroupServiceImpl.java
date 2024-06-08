package com.salmon.chatService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.salmon.chatService.common.ErrorCode;
import com.salmon.chatService.exception.BusinessException;
import com.salmon.chatService.exception.ThrowUtils;
import com.salmon.chatService.model.enums.contact.UserContactTypeEnum;
import com.salmon.chatService.model.enums.group.GroupStatusEnum;
import com.salmon.chatService.model.enums.userContact.UserContactStatusEnum;
import com.salmon.chatService.model.po.Group;
import com.salmon.chatService.mapper.GroupMapper;
import com.salmon.chatService.model.po.UserContact;
import com.salmon.chatService.model.vo.app.SystemConfigVo;
import com.salmon.chatService.service.AppService;
import com.salmon.chatService.service.GroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.salmon.chatService.service.UserContactService;
import com.salmon.chatService.utils.Utils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * <p>
 * 群组 服务实现类
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

    /**
     * 用户创建或修改群组
     *
     * @param group 群组
     */
    @Override
    @Transactional
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
            LambdaUpdateWrapper<Group> updateWrapper = new LambdaUpdateWrapper<>(group);
            ThrowUtils.throwIf(!this.update(updateWrapper), ErrorCode.OPERATION_ERROR, "保存失败");
            // todo 更新相关表冗余信息

            // todo 修改群昵称发送ws消息
        }
        // todo 封面图片 路径 + 群组ID 区分 原图，缩略图
    }
}
