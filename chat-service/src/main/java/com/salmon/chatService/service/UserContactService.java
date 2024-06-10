package com.salmon.chatService.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.salmon.chatService.model.dto.contact.ApplyRequest;
import com.salmon.chatService.model.dto.contact.SearchRequest;
import com.salmon.chatService.model.po.UserContact;
import com.salmon.chatService.model.vo.contact.ApplyResultVO;
import com.salmon.chatService.model.vo.contact.ContactInfoVO;
import com.salmon.chatService.model.vo.contact.SearchContactVO;
import com.salmon.chatService.model.vo.contact.UserContactVO;

import java.util.List;

/**
 * <p>
 * 联系人 服务类
 * </p>
 *
 * @author Salmon
 * @since 2024-06-08
 */
public interface UserContactService extends IService<UserContact> {

    /**
     * 查询联系人信息
     *
     * @param contactId   联系人ID
     * @param contactType 联系人类型
     * @param status      关系状态
     */
    List<UserContactVO> selectContactUserInfo(Long contactId, Integer contactType, Integer status);

    /**
     * 搜索联系人
     *
     * @param request 搜索请求
     * @return SearchContactVO
     */
    SearchContactVO search(SearchRequest request);

    /**
     * 申请加入好友/群聊
     *
     * @param request 申请加入请求
     * @return ApplyResultVO
     */
    ApplyResultVO applyAdd(ApplyRequest request);

    /**
     * 查询联系人关系
     *
     * @param userId      用户ID
     * @param contactId   联系人ID
     * @param contactType 联系人类型
     * @return UserContact
     */
    UserContact selectContact(Integer userId, Integer contactId, Integer contactType);

    /**
     * 添加联系人
     *
     * @param userId        用户ID
     * @param contactId     联系人ID/群ID
     * @param receiveUserId 联系人ID/群管理员ID
     * @param contactType   联系类型
     */
    void addContact(Integer userId, Integer contactId, Integer receiveUserId, Integer contactType);

    /**
     * 加载联系人列表
     *
     * @param contactType 类型
     * @return List<UserContactVO>
     */
    List<UserContactVO> loadContact(Integer contactType);

    /**
     * 获取联系人详情（不一定是好友，比如群成员里看详情））
     *
     * @param id 联系人ID
     * @return ContactInfoVO
     */
    ContactInfoVO getContactInfo(Long id);

    /**
     * 获取联系人详情（必须是好友（包含被拉黑或者被删））
     *
     * @param id 联系人ID
     * @return ContactInfoVO
     */
    ContactInfoVO getContactUserInfo(Long id);
}
