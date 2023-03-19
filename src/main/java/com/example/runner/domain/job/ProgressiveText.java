package com.example.runner.domain.job;

import lombok.Data;

@Data
public class ProgressiveText {
    private String text;
    private int size;
    private boolean hasMoreDate;
}
