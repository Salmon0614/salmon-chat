package com.salmon.chatService.service.impl;

import com.salmon.chatService.model.po.Group;
import com.salmon.chatService.mapper.GroupMapper;
import com.salmon.chatService.service.GroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
