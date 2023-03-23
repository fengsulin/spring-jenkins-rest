package com.example.runner.config;

import com.example.runner.auth.IAuth;
import com.example.runner.auth.JenkinsAuthentication;
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
    public JenkinsAuthentication jenkinsAuthentication(){
        String credentials = jenkinsProperties.getUsername() + ":" +jenkinsProperties.getCredential();
        JenkinsAuthentication authentication = null;
        if (jenkinsProperties.isUsernameApiToken()){
            authentication = JenkinsAuthentication.builder()
                    .apiToken(credentials)
                    .build();
        } else if (jenkinsProperties.isUsernamePassword()) {
            authentication = JenkinsAuthentication.builder()
                    .credentials(credentials)
                    .build();
        }else {
            authentication = JenkinsAuthentication.builder()
                    .build();
        }
        return authentication;
    }
}
