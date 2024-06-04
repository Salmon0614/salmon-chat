package com.salmon.chatService.config.web;

import com.salmon.chatService.interceptor.WebSecurityHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * web mvc配置
 *
 * @author Salmon
 * @since 2024-06-05
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public WebSecurityHandler getWebSecurityHandler() {
        return new WebSecurityHandler();
    }

    /**
     * 配置拦截器
     *
     * @param registry 拦截器注册表
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getWebSecurityHandler());
    }

}
