package com.example.ci.domain.job;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

/**
 * @author: FSL
 * @date: 2023/3/15
 * @description: TODO
 */
@Data
public class Cause {
    @JsonAlias("_class")
    private String clazz;
    private String shortDescription;
    private String userId;
    private String userName;
}
