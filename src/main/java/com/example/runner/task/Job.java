package com.example.runner.task;

import lombok.Data;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author: FSL
 * @date: 2023/3/16
 * @description: 新建Quartz的Job任务 TODO 后续待结合业务使用
 */
@Data
public class Job implements org.quartz.Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

    }
}
