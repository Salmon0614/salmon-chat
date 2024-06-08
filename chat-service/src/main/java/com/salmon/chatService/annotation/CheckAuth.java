package com.salmon.chatService.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 校验权限
 *
 * @author Salmon
 * @since 2024-06-08
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckAuth {

    /**
     * 是否需要登录
     */
    boolean needLogin() default true;

    /**
     * 是否需要管理员权限
     */
    boolean needAdmin() default false;
}
