package com.example.ci.domain.job;

import lombok.Data;

@Data
public class PipelineNodeLog {
    private String nodeId;
    private String nodeStatus;
    private int length;
    private boolean hasMore;
    private String text;
    private String consoleUrl;
}
