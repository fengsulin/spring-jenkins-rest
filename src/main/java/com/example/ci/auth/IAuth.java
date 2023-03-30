package com.example.ci.auth;

public interface IAuth {
    String getAuth();
    default String getBaseUrl(){
        return "http://localhost:8080";
    };
}
