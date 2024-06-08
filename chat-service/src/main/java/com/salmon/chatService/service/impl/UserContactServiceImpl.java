package com.salmon.chatService.service.impl;

import com.salmon.chatService.model.po.UserContact;
import com.salmon.chatService.mapper.UserContactMapper;
import com.salmon.chatService.service.UserContactService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
