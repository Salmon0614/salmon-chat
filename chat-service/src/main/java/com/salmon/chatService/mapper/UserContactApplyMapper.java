package com.salmon.chatService.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.salmon.chatService.model.po.UserContactApply;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.salmon.chatService.model.vo.contact.ApplyRecordVO;
import org.apache.ibatis.annotations.*;

/**
 * <p>
 * 联系人申请 Mapper 接口
 * </p>
 *
 * @author Salmon
 * @since 2024-06-08
 */
@Mapper
public interface UserContactApplyMapper extends BaseMapper<UserContactApply> {

    @Select("select " +
            "uca.*, " +
            "u.area as area, " +
            "u.gender as gender, " +
            "case " +
            "when uca.contact_type=0 " +
            "then u.avatar " +
            "when uca.contact_type=1 " +
            "then g.group_cover " +
            "end as avatar, " +
            "case " +
            "when uca.contact_type=0 " +
            "then u.nickname " +
            "when uca.contact_type=1 " +
            "then g.group_name " +
            "end as contactName " +
            "from tb_user_contact_apply uca " +
            "left join tb_user u " +
            "on uca.apply_user_id=u.id and uca.receive_user_id=#{receiveUserId} " +
            "left join tb_group g " +
            "on uca.contact_id=g.id and uca.receive_user_id=#{receiveUserId} " +
            "where " +
            "uca.receive_user_id=#{receiveUserId} " +
            "order by uca.last_apply_time desc")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "apply_user_id", property = "applyUserId"),
            @Result(column = "contact_type", property = "contactType"),
            @Result(column = "contact_id", property = "contactId"),
            @Result(column = "last_apply_time", property = "lastApplyTime"),
            @Result(column = "status", property = "status"),
            @Result(column = "origin_type", property = "originType"),
            @Result(column = "apply_info", property = "applyInfo"),
            @Result(column = "area", property = "area"),
            @Result(column = "gender", property = "gender"),
            @Result(column = "avatar", property = "avatar"),
            @Result(column = "contactName", property = "contactName")
    })
    Page<ApplyRecordVO> loadApply(@Param("receiveUserId") Integer receiveUserId, Page<UserContactApply> page);
}
