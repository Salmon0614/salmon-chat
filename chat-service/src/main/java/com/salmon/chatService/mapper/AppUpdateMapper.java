package com.salmon.chatService.mapper;

import com.salmon.chatService.model.po.AppUpdate;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * APP发布 Mapper 接口
 * </p>
 *
 * @author Salmon
 * @since 2024-07-10
 */
@Mapper
public interface AppUpdateMapper extends BaseMapper<AppUpdate> {

    @Select("select * from tb_app_update " +
            "where " +
            "app_version>#{appVersion} " +
            "and (" +
            "status = 2 or " +
            "(status = 1 and find_in_set(#{userId},grayscale_uid))" +
            ") " +
            "order by id desc limit 0,1")
    AppUpdate checkVersion(@Param("userId") Integer userId, @Param("appVersion") String appVersion);
}
