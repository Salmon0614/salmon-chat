package com.salmon.chatService.config.openapi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * openapi 配置
 *
 * @author Salmon
 * @since 2024-05-22
 */
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI api() {
        return new OpenAPI().info(new Info().title("接口文档")
                .contact(new Contact().name("Salmon").url("https://chat.salmonedu.ltd").email("1179732961@qq.com"))
                .description("Salmon Chat（类似微信、QQ的聊天服务）")
                .version("v1.0.0"));
    }
}
