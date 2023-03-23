package com.example.runner.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
@ConfigurationProperties(prefix = "jenkins")
public class JenkinsProperties {
    private Boolean usernamePassword;
    private Boolean usernameApiToken;
    private Boolean anonymous;
    private String username;
    private String credential;
    private String url;

    public JenkinsProperties(){}

    public JenkinsProperties(boolean usernamePassword, boolean usernameApiToken, boolean anonymous, String username, String credential, String url) {
        this.usernamePassword = usernamePassword;
        this.usernameApiToken = usernameApiToken;
        this.anonymous = anonymous;
        this.username = username;
        this.credential = credential;
        this.url = url;
    }

    public boolean isUsernamePassword() {
        return usernamePassword;
    }

    public void setUsernamePassword(boolean usernamePassword) {
        this.usernamePassword = usernamePassword;
    }

    public boolean isUsernameApiToken() {
        return usernameApiToken;
    }

    public void setUsernameApiToken(boolean usernameApiToken) {
        this.usernameApiToken = usernameApiToken;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }

    public String getUrl() {
        if (StringUtils.hasText(url)){
            if (url.endsWith("/")){
                StringBuilder builder = new StringBuilder(url);
                builder.delete(builder.length() - 1, builder.length());
                return builder.toString();
            }
        }
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
