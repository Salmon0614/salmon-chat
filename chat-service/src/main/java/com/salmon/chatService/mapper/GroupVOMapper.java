package com.salmon.chatService.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.salmon.chatService.model.po.Group;
import com.salmon.chatService.model.vo.group.GroupVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 群聊 Mapper 接口
 * </p>
 *
 * @author Salmon
 * @since 2024-06-08
 */
@Mapper
public interface GroupVOMapper extends BaseMapper<GroupVO> {

    String querySql = "select " +
            "g.id as id, " +
            "g.group_number as groupNumber, " +
            "g.group_name as groupName, " +
            "g.group_owner_id as groupOwnerId, " +
            "g.group_owner_account as groupOwnerAccount, " +
            "g.group_notice as groupNotice, " +
            "g.group_cover as groupCover, " +
            "g.join_type as joinType, " +
            "g.status as status, " +
            "g.create_time as createTime, " +
            "(select u.nickname from tb_user u where g.group_owner_id = u.id ) as groupOwnerNickname, " +
            "(select count(1) from tb_user_contact uc where uc.contact_type = 1 and uc.contact_id = g.id and uc.status = 1) as memberCount " +
            "from tb_group g";
    String wrapperSql = querySql + " ${ew.customSqlSegment}";

    /**
     * 分页查询
     */
    @Select(wrapperSql)
    Page<GroupVO> page(Page<?> page, @Param("ew") Wrapper<?> queryWrapper);

}
