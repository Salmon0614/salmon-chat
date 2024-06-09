package com.salmon.chatService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.salmon.chatService.model.dto.contact.SearchRequest;
import com.salmon.chatService.model.enums.contact.UserContactTypeEnum;
import com.salmon.chatService.model.enums.userContact.UserContactStatusEnum;
import com.salmon.chatService.model.po.Group;
import com.salmon.chatService.model.po.User;
import com.salmon.chatService.model.po.UserContact;
import com.salmon.chatService.mapper.UserContactMapper;
import com.salmon.chatService.model.vo.account.TokenUserVo;
import com.salmon.chatService.model.vo.contact.SearchContactVO;
import com.salmon.chatService.model.vo.contact.UserContactVO;
import com.salmon.chatService.service.GroupService;
import com.salmon.chatService.service.UserContactService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.salmon.chatService.service.UserService;
import com.salmon.chatService.utils.UserHolder;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

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

    @Resource
    private UserService userService;
    @Resource
    @Lazy
    private GroupService groupService;

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

    /**
     * 搜索联系人
     *
     * @param request 搜索请求
     * @return SearchContactVO
     */
    @Override
    public SearchContactVO search(SearchRequest request) {
        TokenUserVo tokenUserVo = UserHolder.getUser();
        String account = request.getContactAccount();
        UserContactTypeEnum contactTypeEnum = UserContactTypeEnum.getByPrefix(account);
        if (Objects.isNull(contactTypeEnum)) {
            return null;
        }
        SearchContactVO searchContactVO = new SearchContactVO();
        switch (contactTypeEnum) {
            case USER -> {
                User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getAccount, account));
                if (Objects.isNull(user)) {
                    return null;
                }
                searchContactVO.setName(user.getNickname());
                searchContactVO.setAvatar(user.getAvatar());
                searchContactVO.setGender(user.getGenderDesc());
                searchContactVO.setArea(user.getArea());
                searchContactVO.setId(user.getId());
            }
            case GROUP -> {
                Group group = groupService.getOne(new LambdaQueryWrapper<Group>().eq(Group::getGroupNumber, account));
                if (Objects.isNull(group)) {
                    return null;
                }
                searchContactVO.setName(group.getGroupName());
                searchContactVO.setAvatar(group.getGroupCover());
                searchContactVO.setId(group.getId());
            }
        }
        searchContactVO.setContactType(contactTypeEnum.getType());
        searchContactVO.setAccount(account);

        // 搜出来的是自己
        if (tokenUserVo.getAccount().equals(account)) {
            searchContactVO.setStatus(UserContactStatusEnum.FRIEND.getValue());
            return searchContactVO;
        }
        // 查询是否是好友
        UserContact userContact = this.getOne(new LambdaQueryWrapper<UserContact>()
                .eq(UserContact::getUserId, tokenUserVo.getId())
                .eq(UserContact::getContactId, searchContactVO.getId())
                .eq(UserContact::getContactType, searchContactVO.getContactType())
        );
        if (Objects.nonNull(userContact)) {
            searchContactVO.setStatus(userContact.getStatus());
        }
        return searchContactVO;
    }
}
