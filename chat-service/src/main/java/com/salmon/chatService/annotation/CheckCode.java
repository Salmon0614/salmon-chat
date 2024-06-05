package com.salmon.chatService.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 验证码校验
 *
 * @author Salmon
 * @since 2024-06-05
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckCode {

    /**
     * 类型 0-图形验证码
     */
    int type() default 0;
}
