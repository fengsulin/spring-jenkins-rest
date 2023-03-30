package com.example.ci.domain.credential;

import lombok.Data;

/**
 * @author: FSL
 * @date: 2023/3/29
 * @description: TODO
 */
@Data
public class Credential {
    private String id;
    private String typeName;
    private String displayName;
    private String fullName;
    private String description;
}
