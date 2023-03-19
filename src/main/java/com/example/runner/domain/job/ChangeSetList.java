package com.example.runner.domain.job;

import lombok.Data;

import java.util.List;

/**
 * @author: FSL
 * @date: 2023/3/15
 * @description: TODO
 */
@Data
public class ChangeSetList {
    private List<ChangeSet> items;
    private String kind;
}
