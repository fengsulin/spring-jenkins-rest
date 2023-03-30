package com.example.ci.domain.queue;

import com.example.ci.resolver.CustomJsonDateDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.util.Map;

/**
 * @author: FSL
 * @date: 2023/3/15
 * @description: 队列条目详情
 */
@Data
public class QueueItem {
    private boolean blocked;
    private boolean buildable;
    private int id;
    private long inQueueSince;
    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    private Map<String,String> params;
    private boolean stuck;
    private Task task;
    private String url;
    private String why;
    private long buildableStartMilliseconds;
    private boolean cancelled;
    private Executable executable;
    private long timestamp;
}
