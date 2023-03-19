package com.example.runner.domain.job;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.util.List;

/**
 * @author: FSL
 * @date: 2023/3/15
 * @description: TODO
 */
@Data
public class Action {
    private List<Cause> causes;
    private List<Parameter> parameters;
    private String text;
    private String iconPath;
    @JsonAlias("_class")
    private String clazz;
}
