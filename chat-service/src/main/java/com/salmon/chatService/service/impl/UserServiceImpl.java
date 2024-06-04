package com.salmon.chatService.service.impl;

import com.salmon.chatService.model.po.User;
import com.salmon.chatService.mapper.UserMapper;
import com.salmon.chatService.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author Salmon
 * @since 2024-06-04
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
