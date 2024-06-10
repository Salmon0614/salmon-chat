package com.salmon.chatService.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.salmon.chatService.common.PageRequest;
import com.salmon.chatService.mapper.UserContactApplyMapper;
import com.salmon.chatService.model.po.UserContactApply;
import com.salmon.chatService.model.vo.account.TokenUserVo;
import com.salmon.chatService.model.vo.contact.ApplyRecordVO;
import com.salmon.chatService.service.UserContactApplyService;
import com.salmon.chatService.utils.UserHolder;
import org.springframework.stereotype.Service;


/**
 * <p>
 * 联系人申请 服务实现类
 * </p>
 *
 * @author Salmon
 * @since 2024-06-08
 */
@Service
public class UserContactApplyServiceImpl extends ServiceImpl<UserContactApplyMapper, UserContactApply> implements UserContactApplyService {

    @Override
    public Page<ApplyRecordVO> loadApply(PageRequest request) {
        TokenUserVo tokenUserVo = UserHolder.getUser();
        long current = request.getCurrent();
        long size = request.getPageSize();
        return this.baseMapper.loadApply(tokenUserVo.getId(), new Page<>(current, size));
    }
}
