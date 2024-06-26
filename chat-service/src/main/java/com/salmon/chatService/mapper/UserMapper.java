package com.salmon.chatService.mapper;

import com.salmon.chatService.model.po.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户 Mapper 接口
 * </p>
 *
 * @author Salmon
 * @since 2024-06-04
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
