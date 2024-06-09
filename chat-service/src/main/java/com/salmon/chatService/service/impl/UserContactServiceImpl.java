package com.salmon.chatService.service.impl;

import com.salmon.chatService.model.po.UserContact;
import com.salmon.chatService.mapper.UserContactMapper;
import com.salmon.chatService.model.vo.contact.UserContactVO;
import com.salmon.chatService.service.UserContactService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
