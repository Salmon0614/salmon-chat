package com.salmon.chatService.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.salmon.chatService.common.PageRequest;
import com.salmon.chatService.model.dto.contact.DealWithApplyRequest;
import com.salmon.chatService.model.po.UserContactApply;
import com.baomidou.mybatisplus.extension.service.IService;
import com.salmon.chatService.model.vo.contact.ApplyRecordVO;
import com.salmon.chatService.model.vo.userContactApply.UserContactApplyVO;

import java.util.List;

/**
 * <p>
 * 联系人申请 服务类
 * </p>
 *
 * @author Salmon
 * @since 2024-06-08
 */
public interface UserContactApplyService extends IService<UserContactApply> {

    /**
     * 加载申请列表
     *
     * @param request 分页请求
     * @return Page<ApplyRecordVO>
     */
    Page<ApplyRecordVO> loadApply(PageRequest request);

    /**
     * 处理申请请求
     *
     * @param request 申请请求
     */
    void dealWithApply(DealWithApplyRequest request);

    /**
     * 查询离线期间的好友申请记录
     *
     * @param userId      用户ID
     * @param lastOffTime 最后的离线时间
     */
    List<UserContactApplyVO> selectNewApplyListInOffline(Integer userId, Long lastOffTime);

    /**
     * 查询离线期间的好友申请记录数量
     *
     * @param userId      用户ID
     * @param lastOffTime 最后的离线时间
     */
    Integer selectNewApplyCountInOffline(Integer userId, Long lastOffTime);
}
