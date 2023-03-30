package com.example.ci.domain.job;

import lombok.Data;

import java.util.List;

/**
 * @author: FSL
 * @date: 2023/3/15
 * @description: TODO
 */
@Data
public class ChangeSet {
    private List<String> affectedPaths;
    private String commitId;
    private long timestamp;
    private Culprit author;
    private String authorEmail;
    private String commont;
}
