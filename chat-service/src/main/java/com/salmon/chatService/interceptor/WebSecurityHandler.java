package com.salmon.chatService.interceptor;

import com.alibaba.fastjson.JSON;
import com.salmon.chatService.annotation.AccessLimit;
import com.salmon.chatService.common.BaseResponse;
import com.salmon.chatService.common.ErrorCode;
import com.salmon.chatService.common.GetCodeMethodEnum;
import com.salmon.chatService.common.ResultUtils;
import com.salmon.chatService.constant.RedisPrefixConstant;
import com.salmon.chatService.utils.IpUtils;
import com.salmon.chatService.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import static com.salmon.chatService.constant.CommonConstant.APPLICATION_JSON;

/**
 * 限流拦截
 *
 * @author Salmon
 * @since 2024-06-05
 */
@Slf4j
public class WebSecurityHandler implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果请求是controller处理方法
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            AccessLimit accessLimit = handlerMethod.getMethodAnnotation(AccessLimit.class);
            if (accessLimit != null) {
                // 系统相关逻辑获取的，不必处理
                String type = request.getHeader("type");
                log.info("type:" + type);
                log.info("type:{}", type);
                if (GetCodeMethodEnum.SYSTEM.getValue().equals(type)) {
                    return true;
                }
                int maxCount = accessLimit.maxCount();
                int second = accessLimit.second();
                String ipAddress = IpUtils.getIpAddress(request);
                String limit_key = RedisPrefixConstant.ACCESS_LIMIT + ipAddress + handlerMethod.getMethod().getName();
                // 从redis中获取用户访问的次数
                try {
                    long curCount = RedisUtils.incrExpire(limit_key, second);
                    if (curCount > maxCount) {
                        render(response, ResultUtils.error(ErrorCode.LIMIT_ERROR));
                        log.warn(type + ": " + limit_key + "请求次数超过每" + second + "秒" + maxCount + "次");
                        return false;
                    }
                    return true;
                } catch (RedisConnectionFailureException e) {
                    log.warn("redis 操作异常: " + e.getMessage());
                    return false;
                }
            }
        }
        return true;
    }

    private void render(HttpServletResponse response, BaseResponse<Object> result) throws IOException {
        response.setContentType(APPLICATION_JSON);
        OutputStream out = response.getOutputStream();
        String str = JSON.toJSONString(result);
        out.write(str.getBytes(StandardCharsets.UTF_8));
        out.flush();
        out.close();
    }
}
