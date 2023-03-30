package com.example.ci.domain.job;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.util.List;

/**
 * @author: FSL
 * @date: 2023/3/15
 * @description: Job集合类
 */
@Data
public class JobList {
    @JsonAlias("_class")
    private  String clazz;

    private List<Job> jobs;

    private String url;
}
