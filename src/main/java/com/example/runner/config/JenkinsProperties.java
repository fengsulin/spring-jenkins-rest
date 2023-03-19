package com.example.runner.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jenkins")
public class JenkinsProperties {
    /**用户名*/
    private String username;
    /**用户token*/
    private String token;
    /**jenkins访问地址*/
    private String url;

    public JenkinsProperties(){}
    public JenkinsProperties(String username, String token, String url) {
        this.username = username;
        this.token = token;
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
