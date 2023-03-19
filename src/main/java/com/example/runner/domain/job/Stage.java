package com.example.runner.domain.job;

import lombok.Data;

@Data
public class Stage {
    private String id;  // 这里的id就是nodeId
    private String name;
    private String status;
    private long startTimeMills;
    private long endTimeMills;
    private long pauseDurationMills;
    private long durationMills;
}
