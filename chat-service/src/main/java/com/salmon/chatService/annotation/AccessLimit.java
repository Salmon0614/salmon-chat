package com.salmon.chatService.annotation;

import java.lang.annotation.*;

/**
 * 基于 Redis 限流
 *
 * @author Salmon
 * @since 2024-06-05
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccessLimit {

    /**
     * 请求次数的指定时间范围秒数(redis 数据过期时间)
     */
    int second() default 60;

    /**
     * 指定 second 时间内 API 请求次数
     */
    int maxCount() default 5;
}
