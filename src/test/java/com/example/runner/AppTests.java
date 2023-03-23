package com.example.runner;

import com.example.runner.client.JenkinsApi;
import com.example.runner.domain.job.JobList;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AppTests {

    @Resource
    JenkinsApi jenkinsApi;

    @Test
    void contextLoads() {
        JobList jobList = jenkinsApi.jobsApi().jobList("", null, null);
        System.out.println(jobList);
    }

}
