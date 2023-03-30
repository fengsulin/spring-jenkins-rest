package com.example.ci.client;

import com.example.ci.api.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * @author: FSL
 * @date: 2023/3/20
 * @description: 接口聚合
 */
@Component
@RequiredArgsConstructor
@Lazy
public class JenkinsApi {
    private final JobsApi jobsApi;
    private final CrumbIssuerApi crumbIssuerApi;
    private final PluginManagerApi pluginManagerApi;
    private final QueueApi queueApi;
    private final StatisticsApi statisticsApi;
    private final SystemApi systemApi;
    private final UserApi userApi;

    public JobsApi jobsApi() {
        return jobsApi;
    }

    public CrumbIssuerApi crumbIssuerApi() {
        return crumbIssuerApi;
    }

    public PluginManagerApi pluginManagerApi() {
        return pluginManagerApi;
    }

    public QueueApi queueApi() {
        return queueApi;
    }

    public StatisticsApi statisticsApi() {
        return statisticsApi;
    }

    public SystemApi systemApi() {
        return systemApi;
    }

    public UserApi userApi() {
        return userApi;
    }
}
