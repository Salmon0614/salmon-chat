package com.salmon.chatService.config.app;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * APP 配置
 *
 * @author Salmon
 * @since 2024-06-05
 */
@Configuration
@Data
@ConfigurationProperties(prefix = "app")
public class AppConfig {

    /**
     * 指定管理员账号
     */
    private List<String> accounts;
    /**
     * 指定管理员邮箱
     */
    private List<String> emails;
    /**
     * 指定管理员手机
     */
    private List<String> phones;
}
