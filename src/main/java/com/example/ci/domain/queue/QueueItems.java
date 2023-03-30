package com.example.ci.domain.queue;

import lombok.Data;

import java.util.List;

@Data
public class QueueItems {
    private String clazz;
    private List<QueueItem> items;
}
