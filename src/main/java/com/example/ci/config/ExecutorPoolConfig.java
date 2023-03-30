package com.example.ci.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author: FSL
 * @date: 2023/3/29
 * @description: 异步线程池配置类
 */
@Configuration
@EnableAsync
@Slf4j
public class ExecutorPoolConfig {
    /**核心线程数*/
    @Value("${executor.core-pool-size}")
    private int corePoolSize = 10;
    /**最大线程数*/
    @Value("${executor.max-pool-size}")
    private int maxPoolSize = 20;
    /**允许线程空闲时间*/
    @Value("${executor.keep-alive-time}")
    private int keepAliveTime = 60;
    /**缓冲队列大小*/
    @Value("${executor.queue-capacity}")
    private int queueCapacity = 300;
    @Value("${executor.thread-prefix}")
    private String threadPrefix = "X-ThreadAsync-";

    @Bean("asyncServiceExecutor")
    public Executor asyncServiceExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 配置核心线程数
        executor.setCorePoolSize(corePoolSize);
        // 配置最大线程数
        executor.setMaxPoolSize(corePoolSize * 2);
        // 配置队列大小
        executor.setQueueCapacity(queueCapacity);
        // 配置线程前缀名
        executor.setThreadNamePrefix(threadPrefix);
        // 配置线程空闲时间
        executor.setKeepAliveSeconds(keepAliveTime);
        // 当pool达到最大时，不在新线程中执行任务，而是由调用者所在的线程执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 线程池初始化
        executor.initialize();
        log.info("[asyncServiceExecutor异步线程池]:初始化完成");
        return executor;
    }
}
