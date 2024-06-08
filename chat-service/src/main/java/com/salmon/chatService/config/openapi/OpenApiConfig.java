package com.salmon.chatService.config.openapi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OperationCustomizer;
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

    @Bean
    public GroupedOpenApi tokenApi(OperationCustomizer operationCustomizer) {
        return GroupedOpenApi.builder()
                .group("鉴权")
                .packagesToScan("com.salmon.chatService.controller")
                .pathsToMatch("/**")
                .addOperationCustomizer(operationCustomizer)
                .build();
    }

    @Bean
    public OperationCustomizer operationCustomizer() {
        return (operation, handlerMethod) -> operation.addParametersItem(
                new HeaderParameter()
                        .name("token")
                        .required(false)
                        .description("token 验证")
        );
    }
}
