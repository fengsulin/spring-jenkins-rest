package com.example.runner.domain.job;

import lombok.Data;

import java.util.List;

/**
 * @author: FSL
 * @date: 2023/3/15
 * @description: TODO
 */
@Data
public class BuildInfo {
    private List<Artifact> artifacts;
    private List<Action> actions;
    private boolean building;
    private String description;
    private String displayName;
    private long duration;
    private long estimatedDuration;
    private String fullDisplayName;
    private String id;
    private boolean keepLog;
    private int number;
    private int queueId;
    private String result;
    private long timestamp;
    private List<ChangeSetList> changeSets;
    private String builtOn;
    private List<Culprit> culprits;
}
