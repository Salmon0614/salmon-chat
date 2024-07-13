package com.salmon.chatService.config.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.salmon.chatService.constant.Settings;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * mybatis plus自动填充
 *
 * @author Salmon
 * @since 2024-05-21
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.debug("start insert fill ....");
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now(ZoneId.of(Settings.ZONE_ID)));
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now(ZoneId.of(Settings.ZONE_ID)));
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.debug("start update fill ....");
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now(ZoneId.of(Settings.ZONE_ID)));
    }


}
