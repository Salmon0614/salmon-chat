package com.salmon.chatService.mapper;

import com.salmon.chatService.model.po.UserContact;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.salmon.chatService.model.vo.contact.UserContactVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 联系人 Mapper 接口
 * </p>
 *
 * @author Salmon
 * @since 2024-06-08
 */
@Mapper
public interface UserContactMapper extends BaseMapper<UserContact> {

    @Select(value = "SELECT " +
            "c.id as id, " +
            "c.user_id as userId, " +
            "c.contact_id as contactId, " +
            "u.account as account, " +
            "u.nickname as nickname, " +
            "u.description as description, " +
            "u.mobile as mobile, " +
            "u.email as email, " +
            "u.avatar as avatar, " +
            "u.gender as gender, " +
            "u.area as area, " +
            "u.last_login_time as lastLoginTime, " +
            "u.last_off_time as lastOffTime " +
            "FROM tb_user_contact as c " +
            "INNER JOIN tb_user as u " +
            "ON c.user_id = u.id " +
            "WHERE c.contact_id = #{contactId} " +
            "AND c.contact_type = #{contactType} " +
            "AND c.status = #{status} " +
            "ORDER BY c.id ASC")
    List<UserContactVO> selectContactUserInfo(@Param("contactId") Long contactId, @Param("contactType") Integer contactType, @Param("status") Integer status);
}
