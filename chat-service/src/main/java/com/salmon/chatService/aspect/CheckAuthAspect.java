package com.salmon.chatService.aspect;

import com.salmon.chatService.annotation.CheckAuth;
import com.salmon.chatService.common.ErrorCode;
import com.salmon.chatService.common.UserRoleEnum;
import com.salmon.chatService.constant.CommonConstant;
import com.salmon.chatService.constant.RedisPrefixConstant;
import com.salmon.chatService.exception.BusinessException;
import com.salmon.chatService.exception.ThrowUtils;
import com.salmon.chatService.model.vo.account.TokenUserVo;
import com.salmon.chatService.utils.RedisUtils;
import com.salmon.chatService.utils.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 权限校验切面
 *
 * @author Salmon
 * @since 2024-06-08
 */
@Component
@Aspect
@Slf4j
public class CheckAuthAspect {

    /**
     * 切入点，在注解的位置切入代码
     */
    @Pointcut("@annotation(com.salmon.chatService.annotation.CheckAuth)")
    public void CheckAuthCut() {
    }

    /**
     * 校验接口权限
     *
     * @param joinPoint 切入点
     */
    @Before(value = "CheckAuthCut()")
    public void validAuth(JoinPoint joinPoint) {
        try {
            // 从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            // 获取切入点所在的方法
            Method method = signature.getMethod();
            CheckAuth checkAuthAnnotation = method.getAnnotation(CheckAuth.class);
            if (checkAuthAnnotation == null) {
                return;
            }
            boolean needLogin = checkAuthAnnotation.needLogin();
            boolean needAdmin = checkAuthAnnotation.needAdmin();
            boolean needHolder = checkAuthAnnotation.needHolder();
            // 只有必须登录的，才校验是否需要管理员权限
            if (needLogin) {
                check(needAdmin, needHolder);
            }
        } catch (BusinessException e) {
            throw e;
        } catch (Throwable e) {
            log.error("校验接口权限出现异常: ", e);
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
    }

    /**
     * 方法返回后（无论是否抛异常）移除 threadLocal ，避免内存泄漏
     *
     * @param joinPoint 切入点
     */
    @After(value = "CheckAuthCut()")
    public void removeThreadLocalInAfter(JoinPoint joinPoint) {
        log.debug("After......");
        if (Objects.nonNull(UserHolder.getUser())) {
            UserHolder.removeUser();
        }
    }

    /**
     * 验证
     *
     * @param needAdmin  是否需要管理员
     * @param needHolder 是否需要用户session存储到ThreadLocal
     */
    private void check(boolean needAdmin, boolean needHolder) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String token = request.getHeader(CommonConstant.TOKEN);
        ThrowUtils.throwIf(!StringUtils.hasText(token), ErrorCode.NOT_LOGIN_ERROR);
        TokenUserVo tokenUserVo = RedisUtils.get(RedisPrefixConstant.LOGIN_SESSION + token, TokenUserVo.class);
        ThrowUtils.throwIf(Objects.isNull(tokenUserVo), ErrorCode.NOT_LOGIN_ERROR);
        if (needAdmin) {
            ThrowUtils.throwIf(tokenUserVo.getRole() != UserRoleEnum.ADMIN.getValue(), ErrorCode.FORBIDDEN_ERROR);
        }
        // 存进ThreadLocal
        if (needHolder) {
            UserHolder.setUser(tokenUserVo);
        }
    }
}
