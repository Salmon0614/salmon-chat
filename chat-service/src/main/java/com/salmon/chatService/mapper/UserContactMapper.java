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
            "c.id AS id, " +
            "c.user_id AS userId, " +
            "c.contact_id AS contactId, " +
            "u.account AS contactAccount, " +
            "u.nickname AS contactName, " +
//            "u.description AS description, " +
//            "u.mobile AS mobile, " +
//            "u.email AS email, " +
            "u.avatar AS avatar " +
//            "u.gender AS gender, " +
//            "u.area AS area, " +
//            "u.last_login_time AS lastLoginTime, " +
//            "u.last_off_time AS lastOffTime " +
            "FROM tb_user_contact AS c " +
            "INNER JOIN tb_user AS u " +
            "ON c.user_id = u.id " +
            "WHERE c.contact_id = #{contactId} " +
            "AND c.contact_type = #{contactType} " +
            "AND c.status = #{status} " +
            "AND u.is_delete = 0 " +
            "ORDER BY c.id")
    List<UserContactVO> selectContactUserInfo(@Param("contactId") Integer contactId, @Param("contactType") Integer contactType, @Param("status") Integer status);

    @Select(value = "SELECT " +
            "c.id AS id, " +
            "c.user_id AS userId, " +
            "c.contact_id AS contactId, " +
            "g.group_number AS contactAccount, " +
            "g.group_name AS contactName, " +
            "g.group_cover AS avatar " +
            "FROM tb_user_contact AS c " +
            "INNER JOIN tb_group AS g " +
            "ON c.contact_id = g.id " +
            "AND g.group_owner_id != c.user_id " +
            "WHERE c.user_id = #{userId} " +
            "AND c.contact_type = #{contactType} " +
            "AND c.status in ${status} " +
            "AND g.group_owner_id != #{userId} " +
            "ORDER BY c.id")
    List<UserContactVO> selectContactGroupInfoList(@Param("userId") Integer userId, @Param("contactType") Integer contactType, @Param("status") String status);

    @Select(value = "SELECT " +
            "c.id AS id, " +
            "c.user_id AS userId, " +
            "c.contact_id AS contactId, " +
            "u.account AS contactAccount, " +
            "u.nickname AS contactName, " +
//            "u.description AS description, " +
//            "u.mobile AS mobile, " +
//            "u.email AS email, " +
            "u.avatar AS avatar " +
            "FROM tb_user_contact AS c " +
            "INNER JOIN tb_user AS u " +
            "ON c.contact_id = u.id " +
            "WHERE c.user_id = #{userId} " +
            "AND c.contact_type = #{contactType} " +
            "AND c.status in ${status} " +
            "AND u.is_delete = 0 " +
            "ORDER BY c.id")
    List<UserContactVO> selectContactUserInfoList(@Param("userId") Integer userId, @Param("contactType") Integer contactType, @Param("status") String status);


    @Select("""
            select uc.id         AS id,
                   uc.user_id    AS userId,
                   uc.contact_id AS contactId,
                   case
                       when uc.contact_type = 1 then g.group_number
                       else u.account
                       end       as contactAccount
            from tb_user_contact uc
                     left join tb_user u on u.id = uc.contact_id and uc.contact_type = 0
                     left join tb_group g on g.id = uc.contact_id and uc.contact_type = 1
            where uc.user_id = #{userId}
              and uc.status = #{status}
              and u.is_delete = 0""")
    List<UserContactVO> selectUserContact(@Param("userId") Integer userId, @Param("status") Integer status);
}
