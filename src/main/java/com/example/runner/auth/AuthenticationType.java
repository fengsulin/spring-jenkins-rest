package com.example.runner.auth;

import lombok.Getter;

/**
 * @author: FSL
 * @date: 2023/3/20
 * @description: 认证类型枚举类
 */
public enum AuthenticationType {
    UsernamePassword("UsernamePassword","Basic"),
    UsernameApiToken("UsernameApiToken","Basic"),
    Anonymous("Anonymous","");
    @Getter
    private final String authName;
    @Getter
    private final String authScheme;

     AuthenticationType(String authName, String authScheme) {
        this.authName = authName;
        this.authScheme = authScheme;
    }


    @Override
    public String toString() {
        return authName;
    }
}
