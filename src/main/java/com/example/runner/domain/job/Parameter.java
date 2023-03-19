package com.example.runner.domain.job;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

/**
 * @author: FSL
 * @date: 2023/3/15
 * @description: TODO
 */
@Data
public class Parameter {
    @JsonAlias("_class")
    private String clazz;
    private String name;
    private String value;
}
