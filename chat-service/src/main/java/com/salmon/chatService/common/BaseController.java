package com.salmon.chatService.common;

import com.salmon.chatService.constant.CommonConstant;
import com.salmon.chatService.constant.RedisPrefixConstant;
import com.salmon.chatService.model.vo.account.TokenUserVo;
import com.salmon.chatService.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 父Controller
 *
 * @author Salmon
 * @since 2024-06-04
 */
@Slf4j
public abstract class BaseController {

    @Resource
    private HttpServletRequest servletRequest;

    /**
     * 获取用户登录信息
     *
     * @return TokenUserVo
     */
    protected TokenUserVo getTokenUser() {
        String token = servletRequest.getHeader(CommonConstant.TOKEN);
        if (!StringUtils.hasText(token)) {
            return null;
        }
        return RedisUtils.get(RedisPrefixConstant.LOGIN_SESSION + token, TokenUserVo.class);
    }
}
