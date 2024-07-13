package com.salmon.chatService.mapper;

import com.salmon.chatService.model.po.ChatSessionUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.salmon.chatService.model.vo.chatSessionUser.ChatSessionUserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 聊天会话用户 Mapper 接口
 * </p>
 *
 * @author Salmon
 * @since 2024-07-12
 */
@Mapper
public interface ChatSessionUserMapper extends BaseMapper<ChatSessionUser> {

    @Select("""
            select csu.id,
                   csu.user_account     as userAccount,
                   csu.contact_account  as contactAccount,
                   csu.session_id       as sessionId,
                   csu.contact_name     as contactName,
                   cs.last_message      as lastMessage,
                   cs.last_receive_time as lastReceiveTime,
                   IF(substring(csu.contact_account, 1, 2) = 'SG', (select count(1)
                                                                    from tb_user_contact as uc
                                                                    where uc.contact_id =
                                                                          (select id from tb_group where group_number = csu.contact_account)),
                      0)                as memberCount
            from tb_chat_session_user as csu
                     left join tb_chat_session as cs on csu.session_id = cs.session_id
            where csu.user_account = #{account}
            order by cs.last_receive_time desc""")
    List<ChatSessionUserVO> selectSessionUserList(@Param("account") String account);
}
