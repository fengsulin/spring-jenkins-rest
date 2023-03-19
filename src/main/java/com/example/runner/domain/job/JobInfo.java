package com.example.runner.domain.job;

import com.example.runner.domain.queue.QueueItem;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.util.List;

/**
 * @author: FSL
 * @date: 2023/3/15
 * @description: TODO
 */
@Data
public class JobInfo {
    @JsonAlias("_class")
    private String clazz;
    private String description;
    private String displayName;
    private String fullName;
    private String name;
    private String url;
    private boolean buildable;
    private List<BuildInfo> builds;
    private String color;
    private BuildInfo firstBuild;
    private boolean inQueue;
    private boolean keepDependencies;
    private BuildInfo lastBuild;
    private BuildInfo lastCompleteBuild;
    private BuildInfo lastFailedBuild;
    private BuildInfo lastSuccessfulBuild;
    private BuildInfo lastUnstableBuild;
    private BuildInfo lastUnsuccessfulBuild;
    private int nextBuildNumber;
    private QueueItem queueItem;
    private boolean concurrentBuild;
}
