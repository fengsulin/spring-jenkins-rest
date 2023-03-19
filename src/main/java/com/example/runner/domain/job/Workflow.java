package com.example.runner.domain.job;

import lombok.Data;

import java.util.List;

@Data
public class Workflow {
    private String name;
    private String status;
    private long startTimeMills;
    private long durationTimeMills;
    private List<Stage> stages;
}
