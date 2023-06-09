package com.example.ci.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: FSL
 * @date: 2023/3/15
 * @description: Swagger配置类
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI apiInfo(){
        return new OpenAPI().info(info("Jenkins client OpenApi3.0","v1.1"));
    }

    @Bean
    public GroupedOpenApi helloApi(){
        return GroupedOpenApi.builder()
                .group("JENKINS服务接口管理")
                .packagesToScan("com.example.ci.controller")
                .build();
    }
    private Info info(String title,String version){
        return new Info()
                .title(title)
                .contact(contact("FSL","FSL@163.com","https://github.com/fengsulin"))
                .description("Jenkins api client")
                .version(version)
                .license(new License().name("Apache 2.0").url("http://springdoc.org"));
    }

    private Contact contact(String name,String email,String url){
        Contact contact = new Contact();
        contact.setEmail(email);
        contact.setName(name);
        contact.setUrl(url);
        return contact;
    }
}
