package com.salmon.chatService.mapper;

import com.salmon.chatService.model.po.UserContact;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

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

}
