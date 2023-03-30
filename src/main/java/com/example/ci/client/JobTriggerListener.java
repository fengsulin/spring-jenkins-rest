package com.example.ci.client;

import com.example.ci.api.JobsApi;
import com.example.ci.api.QueueApi;
import com.example.ci.controller.JobController;
import com.example.ci.domain.job.BuildInfo;
import com.example.ci.domain.queue.QueueItems;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author: FSL
 * @date: 2023/3/28
 * @description: 构建直到构建完成
 */
@RequiredArgsConstructor
@Component
@Slf4j
public class JobTriggerListener {

    private final JobsApi jobsApi;
    private static final Long DEFAULT_RETRY_INTERVAL = 1000L;

    /**
     * @description: 构建结束后，将构建结果回传给其他服务
     * @author: FSL
     * @date: 2023/3/30
     * @param path
     * @param jobName
     * @param buildNumber
     * @return: com.example.ci.domain.job.BuildInfo
     */
    @Async("asyncServiceExecutor")
    public void assertJobUntilFinished(String path,String jobName,String buildNumber){
        BuildInfo buildInfo = jobsApi.buildInfo(path, jobName, buildNumber);
        if (buildInfo == null){
            log.info("[buildInfo]:无结果");
            log.debug("当前并发构建job数为：{}", JobController.currentCount.decrementAndGet());
            return;
            // TODO 发送结果
        }
        while (buildInfo.isBuilding()){
            try {
                Thread.sleep(DEFAULT_RETRY_INTERVAL);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            buildInfo = jobsApi.buildInfo(path, jobName, buildNumber);
        }
        log.debug("当前并发构建job数为：{}", JobController.currentCount.decrementAndGet());
        // TODO 发送结果
        log.info("[buildInfo]：{}",buildInfo);
    }

}
