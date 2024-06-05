package com.salmon.chatService.aspect;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.salmon.chatService.annotation.CheckCode;
import com.salmon.chatService.common.CheckCodeTypeEnum;
import com.salmon.chatService.constant.RedisPrefixConstant;
import com.salmon.chatService.exception.BusinessException;
import com.salmon.chatService.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 验证码校验切面
 *
 * @author Salmon
 * @since 2024-06-05
 */
@Component
@Aspect
@Slf4j
public class CheckCodeAspect {

    /**
     * 设置操作日志切入点 记录操作日志 在注解的位置切入代码
     */
    @Pointcut("@annotation(com.salmon.chatService.annotation.CheckCode)")
    public void checkCodeCut() {
    }

    /**
     * 校验code
     *
     * @param joinPoint 切入点
     */
    @Before(value = "checkCodeCut()")
    public void validRedisKey(JoinPoint joinPoint) {
        // 从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 获取切入点所在的方法
        Method method = signature.getMethod();
        // 获取操作
        CheckCode checkCodeAnnotation = method.getAnnotation(CheckCode.class);
        int type = checkCodeAnnotation.type();
        // 获取请求参数内容
        Object obj = joinPoint.getArgs()[0];
        JSONObject jsonObject = JSONUtil.parseObj(obj);
        String checkCodeKey = jsonObject.getStr("checkCodeKey");
        String checkCode = jsonObject.getStr("checkCode");
        String code_key = null;
        if (type == CheckCodeTypeEnum.IMAGE.getValue()) {
            code_key = RedisPrefixConstant.CHECK_CODE + checkCodeKey;
        }
        if (code_key != null) {
            try {
                String code = RedisUtils.get(code_key, String.class);
                if (!checkCode.equalsIgnoreCase(code)) {
                    throw new BusinessException("验证码错误");
                }
            } finally {
                RedisUtils.del(code_key);
            }
        }
    }

//    /**
//     * 正常返回通知，拦截有加验证码注解的请求，连接点正常执行完成后执行，如果连接点抛出异常，则不会执行
//     *
//     * @param joinPoint 切入点
//     * @param returns   返回结果
//     */
//    @AfterReturning(value = "checkCodeCut()", returning = "returns")
//    public void deleteRedisKey(JoinPoint joinPoint, Object returns) {
//        // 从切面织入点处通过反射机制获取织入点处的方法
//        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//        // 获取切入点所在的方法
//        Method method = signature.getMethod();
//        // 获取操作
//        CheckCode checkCodeAnnotation = method.getAnnotation(CheckCode.class);
//    }

}
