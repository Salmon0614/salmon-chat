package com.salmon.chatService.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.salmon.chatService.common.ErrorCode;
import com.salmon.chatService.common.PageRequest;
import com.salmon.chatService.exception.ThrowUtils;
import com.salmon.chatService.mapper.UserContactApplyMapper;
import com.salmon.chatService.model.dto.contact.DealWithApplyRequest;
import com.salmon.chatService.model.enums.contact.ContactApplyStatusEnum;
import com.salmon.chatService.model.enums.userContact.UserContactStatusEnum;
import com.salmon.chatService.model.po.UserContact;
import com.salmon.chatService.model.po.UserContactApply;
import com.salmon.chatService.model.vo.account.TokenUserVo;
import com.salmon.chatService.model.vo.contact.ApplyRecordVO;
import com.salmon.chatService.service.UserContactApplyService;
import com.salmon.chatService.service.UserContactService;
import com.salmon.chatService.utils.UserHolder;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Objects;


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

    @Resource
    @Lazy
    private UserContactService userContactService;

    /**
     * 加载申请列表
     *
     * @param request 分页请求
     * @return Page<ApplyRecordVO>
     */
    @Override
    public Page<ApplyRecordVO> loadApply(PageRequest request) {
        TokenUserVo tokenUserVo = UserHolder.getUser();
        long current = request.getCurrent();
        long size = request.getPageSize();
        return this.baseMapper.loadApply(tokenUserVo.getId(), new Page<>(current, size));
    }

    /**
     * 处理申请请求
     *
     * @param request 申请请求
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void dealWithApply(DealWithApplyRequest request) {
        ContactApplyStatusEnum applyStatusEnum = ContactApplyStatusEnum.getEnumByValue(request.getStatus());
        ThrowUtils.throwIf(Objects.isNull(applyStatusEnum) || applyStatusEnum == ContactApplyStatusEnum.WAIT, ErrorCode.PARAMS_ERROR);
        TokenUserVo tokenUserVo = UserHolder.getUser();
        UserContactApply contactApply = this.getById(request.getApplyId());
        ThrowUtils.throwIf(
                Objects.isNull(contactApply)
                        || !contactApply.getReceiveUserId().equals(tokenUserVo.getId()),
                ErrorCode.PARAMS_ERROR
        );
        ThrowUtils.throwIf(contactApply.getStatus().equals(ContactApplyStatusEnum.WAIT.getValue()), "该申请记录已处理");
        contactApply.setStatus(request.getStatus());
        ThrowUtils.throwIf(!this.updateById(contactApply), "处理失败");
        switch (applyStatusEnum) {
            case PASS -> {
                userContactService.addContact(
                        contactApply.getApplyUserId(),
                        contactApply.getContactId(),
                        contactApply.getReceiveUserId(),
                        contactApply.getContactType()
                );
            }
            case BLACK -> {
                // 只记录申请人知道被拉黑即可
                UserContact userContact = userContactService.selectContact(contactApply.getApplyUserId(), contactApply.getContactId(), contactApply.getContactType());
                if (Objects.isNull(userContact)) {
                    userContact = new UserContact();
                    userContact.setUserId(contactApply.getApplyUserId());
                    userContact.setContactType(contactApply.getContactType());
                    userContact.setContactId(contactApply.getContactId());
                }
                userContact.setStatus(UserContactStatusEnum.FIRST_BE_BLACK.getValue());
                ThrowUtils.throwIf(!userContactService.save(userContact), "处理失败");
            }
        }
    }
}
