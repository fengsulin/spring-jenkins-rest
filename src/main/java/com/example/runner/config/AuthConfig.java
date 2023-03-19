package com.example.runner.config;

import com.example.runner.auth.IAuth;
import com.example.runner.auth.impl.BasicAuth;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthConfig {
    private final JenkinsProperties jenkinsProperties;

    public AuthConfig(JenkinsProperties jenkinsProperties) {
        this.jenkinsProperties = jenkinsProperties;
    }

    @Bean
    public IAuth auth(){
        return new BasicAuth(jenkinsProperties.getUsername(), jenkinsProperties.getToken(), jenkinsProperties.getUrl());
    }
}
