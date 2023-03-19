package com.example.runner.auth;

public interface IAuth {
    String getAuth();
    default String getBaseUrl(){
        return "http://localhost:8080";
    };
}
