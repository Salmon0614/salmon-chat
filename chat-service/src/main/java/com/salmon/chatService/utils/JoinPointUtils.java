package com.salmon.chatService.utils;

import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * JoinPoint 工具类
 *
 * @author Salmon
 * @since 2024-06-05
 */
public class JoinPointUtils {

    /**
     * 从 joinPoint 中 根据 参数名称 获取参数值
     *
     * @param joinPoint 切点
     * @param paramName 参数名称
     * @return 参数值
     */
    public static <T> T getParamByName(JoinPoint joinPoint, String paramName, Class<T> clazz) {
        // 获取所有参数的值
        Object[] args = joinPoint.getArgs();
        // 获取方法签名
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        // 在方法签名中获取所有参数的名称
        String[] parameterNames = methodSignature.getParameterNames();
        // 根据参数名称拿到下标， 参数值的数组和参数名称的数组下标是一一对应的
        int index = ArrayUtils.indexOf(parameterNames, paramName);
        // 在参数数组中取出下标对应参数值
        Object obj = args[index];
        if (obj == null) {
            return null;
        }
        // 将object对象转为Class返回
        if (clazz.isInstance(obj)) {
            return clazz.cast(obj);
        }
        return (T) obj;
    }
}
