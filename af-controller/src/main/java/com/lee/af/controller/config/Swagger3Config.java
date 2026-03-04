package com.lee.af.controller.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
// @Profile({"dev", "test"}) // 仅在 dev/test 环境生效
public class Swagger3Config {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Spring Boot 3 API 文档")
                        .version("1.0")
                        .description("基于 OpenAPI 3.0 的 RESTful API 文档")
                        .contact(new Contact()
                                .name("技术团队")
                                .email("tech@example.com")
                                .url("https://example.com")));
    }
}
