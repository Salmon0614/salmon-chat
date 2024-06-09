package com.salmon.chatService.service;

import com.salmon.chatService.model.po.UserContact;
import com.baomidou.mybatisplus.extension.service.IService;
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
}
