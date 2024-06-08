package com.salmon.chatService.mapper;

import com.salmon.chatService.model.po.Group;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 群组 Mapper 接口
 * </p>
 *
 * @author Salmon
 * @since 2024-06-08
 */
@Mapper
public interface GroupMapper extends BaseMapper<Group> {

}
