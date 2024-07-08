package com.salmon.chatService.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.salmon.chatService.common.IdRequest;
import com.salmon.chatService.model.dto.group.GroupQueryRequest;
import com.salmon.chatService.model.po.Group;
import com.baomidou.mybatisplus.extension.service.IService;
import com.salmon.chatService.model.vo.group.GroupChatVO;
import com.salmon.chatService.model.vo.group.GroupVO;

/**
 * <p>
 * 群聊 服务类
 * </p>
 *
 * @author Salmon
 * @since 2024-06-08
 */
public interface GroupService extends IService<Group> {

    /**
     * 用户创建或修改群组
     *
     * @param group 群聊
     */
    void saveOrUpdateGroup(Group group);

    /**
     * 获取群组信息
     *
     * @param request ID请求
     * @return GroupVO
     */
    GroupVO getGroupInfo(IdRequest request);

    /**
     * 查询群组详情
     *
     * @param groupId 群ID
     * @return Group
     */
    Group getGroupDetail(Integer groupId);

    /**
     * 获取群聊详细信息（包括成员）
     *
     * @param request ID请求
     * @return GroupChatVO
     */
    GroupChatVO getGroupInfo4Chat(IdRequest request);

    /**
     * 后台分页查询群聊
     *
     * @param request 查询请求
     * @return 分页对象
     */
    Page<GroupVO> queryGroupVOPage(GroupQueryRequest request);

    /**
     * 解散群聊
     *
     * @param groupOwnerId 群主id
     * @param groupId      群id
     */
    void dissolutionGroup(Integer groupOwnerId, Integer groupId);
}
