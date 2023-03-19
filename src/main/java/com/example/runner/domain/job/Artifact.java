package com.example.runner.domain.job;

import lombok.Data;

/**
 * @author: FSL
 * @date: 2023/3/15
 * @description: TODO
 */
@Data
public class Artifact {
    private String displayPath;
    private String fileName;
    private String relativePath;
}
