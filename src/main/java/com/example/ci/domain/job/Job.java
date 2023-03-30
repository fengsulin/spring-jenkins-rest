package com.example.ci.domain.job;


import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

/**
 * @author: FSL
 * @date: 2023/3/15
 * @description: Job基类
 */
@Data
public  class Job {
    @JsonAlias("_class")
    private String clazz;

    private String name;

    private String fullName;

    private String url;

    @JsonAlias("color")
    private String color;
}
