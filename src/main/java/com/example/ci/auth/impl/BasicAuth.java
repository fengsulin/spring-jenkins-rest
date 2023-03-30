package com.example.ci.auth.impl;


import com.example.ci.auth.IAuth;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Basic 认证
 */
public class BasicAuth implements IAuth {
    private String username;
    private String password;
    private String baseUrl;

    public BasicAuth(String username,String password,String baseUrl){
        this.username = username;
        this.password = password;
        this.baseUrl = baseUrl;
    }
    @Override
    public String getAuth() {
        String auth = String.format("%s:%s",this.username,this.password);

        byte[] bytes = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.ISO_8859_1));
        return "Basic " + new String(bytes);
    }

    @Override
    public String getBaseUrl(){
        return baseUrl;
    }
}
