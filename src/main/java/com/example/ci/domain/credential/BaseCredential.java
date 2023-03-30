package com.example.ci.domain.credential;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: FSL
 * @date: 2023/3/30
 * @description: TODO
 */
@Data
public class BaseCredential implements Serializable {
    private String id;
    private String description;
}
