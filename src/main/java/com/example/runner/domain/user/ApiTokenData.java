package com.example.runner.domain.user;

import lombok.Data;

@Data
public class ApiTokenData {
    private String tokenName;
    private String tokenUuid;
    private String tokenValue;
}
