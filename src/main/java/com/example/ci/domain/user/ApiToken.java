package com.example.ci.domain.user;

import lombok.Data;

@Data
public class ApiToken {
    private String status;
    private ApiTokenData data;
}
