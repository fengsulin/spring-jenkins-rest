package com.example.runner.controller;

import com.example.runner.constants.HeadersConstants;
import com.example.runner.domain.job.*;
import com.example.runner.remote.JobsApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author: FSL
 * @date: 2023/3/16
 * @description: Jenkins Job 管理接口，封装自己的业务
 */
@RestController
@Tag(name = "Job管理接口")
@RequestMapping("/job")
@AllArgsConstructor
@Slf4j
public class JobController {
    private final JobsApi jobsApi;

    @GetMapping("/list")
    @Operation(summary = "获取目录下Job列表")
    @Parameters({
            @Parameter(required = false,name = "folderPath",description = "单级或多级目录"),
    })
    public JobList getJobList(@RequestParam(required = false,defaultValue = "") String folderPath){
        JobList jobList = jobsApi.jobList(folderPath, 1, "jobs[name,fullName,url]");
        return jobList;
    }

    // TODO 待调试
    @GetMapping("/all")
    @Operation(summary = "获取所有job")
    @Parameters({
            @Parameter(required = false,name = "folderPath",description = "单级或多级目录"),
    })
    public List<Job> getAllJob(@RequestParam(required = false,defaultValue = "") String folderPath){
        JobList jobList = jobsApi.jobList(folderPath, 1, "jobs[name,fullName,url]");
        if (jobList == null || jobList.getJobs() == null || jobList.getJobs().size() <= 0) return Collections.emptyList();
        return exchangeJob(jobList.getJobs());
    }

    @GetMapping("/info")
    @Operation(summary = "获取Job基本信息")
    @Parameters({
            @Parameter(required = false,name = "folderPath",description = "单级或多级目录"),
            @Parameter(required = true,name = "name",description = "流水线名称")
    })
    public JobInfo getJobInfo(@RequestParam(required = false,defaultValue = "") String folderPath, String name){
        JobInfo jobInfo = jobsApi.jobInfo(folderPath, name,0,null);
        return jobInfo;
    }

    @GetMapping("/lastBuildNumber")
    @Operation(summary = "获取流水线最后一次构建的构建编号")
    @Parameters({
            @Parameter(required = false,name = "folderPath",description = "单级或多级目录"),
            @Parameter(required = true,name = "name",description = "流水线名称")
    })
    public String getLastBuildNumber(@RequestParam(required = false,defaultValue = "") String folderPath, String name){
        String lastBuildNumber = jobsApi.lastBuildNumber(folderPath, name);
        return lastBuildNumber;
    }

    @GetMapping("/buildInfo")
    @Operation(summary = "获取构建详情")
    @Parameters({
            @Parameter(required = false,name = "folderPath",description = "单级或多级目录"),
            @Parameter(required = true,name = "name",description = "流水线名称"),
            @Parameter(required = true,name = "number",description = "构建编号，最后一次构建编号可以为lastBuild，其他类似")
    })
    public BuildInfo getBuildInfo(@RequestParam(required = false,defaultValue = "") String folderPath, String name,String number){
        BuildInfo buildInfo = jobsApi.buildInfo(folderPath, name, number);
        return buildInfo;
    }

    @PostMapping(value = "/create",consumes = MediaType.APPLICATION_XML_VALUE)
    @Operation(summary = "新建流水线")
    @Parameters({
            @Parameter(required = false,name = "folderPath",description = "单级或多级目录"),
            @Parameter(required = true,name = "name",description = "流水线名称"),
    })
    public boolean createJob(@RequestParam(required = false,defaultValue = "") String folderPath,
                             String name,
                             @RequestBody String configXML){
        ResponseEntity<String> entity = jobsApi.create(folderPath, name, configXML);
        boolean createF = entity.getStatusCode().is2xxSuccessful();
        if (createF){
            log.info("[" + name +"]:create成功");
        }else {
            log.error("[" + name +"]:create失败");
        }
        return createF;
    }

    @PostMapping(value = "/update",consumes = MediaType.TEXT_XML_VALUE)
    @Operation(summary = "更新流水线")
    @Parameters({
            @Parameter(required = false,name = "folderPath",description = "单级或多级目录"),
            @Parameter(required = true,name = "name",description = "流水线名称"),
    })
    public boolean updateJob(@RequestParam(required = false,defaultValue = "") String folderPath,
                             String name,
                             @RequestBody String configXML) {
        ResponseEntity<String> entity = jobsApi.updateXML(folderPath, name, configXML);
        boolean updateF = entity.getStatusCode().is2xxSuccessful();
        if (updateF){
            log.info("[" + name +"]:update成功");
        }else {
            log.error("[" + name +"]:update失败");
        }
        return updateF;
    }

    @GetMapping(value = "/config")
    @Operation(summary = "获取流水线xml配置")
    @Parameters({
            @Parameter(required = false,name = "folderPath",description = "单级或多级目录"),
            @Parameter(required = true,name = "name",description = "流水线名称"),
    })
    public String getConfig(@RequestParam(required = false,defaultValue = "") String folderPath, String name){
        String config = jobsApi.config(folderPath, name);
        return config;
    }

    @PostMapping(value = "/delete")
    @Operation(summary = "删除流水线")
    @Parameters({
            @Parameter(required = false,name = "folderPath",description = "单级或多级目录"),
            @Parameter(required = true,name = "name",description = "流水线名称"),
    })
    public boolean deleteJob(@RequestParam(required = false,defaultValue = "") String folderPath, String name){
        ResponseEntity<String> entity = jobsApi.delete(folderPath, name);
        boolean deleteF = entity.getStatusCode().is3xxRedirection();
        if (deleteF){
            log.info("[" + name +"]:delete成功");
        }else {
            log.error("[" + name +"]:delete失败");
        }
        return deleteF;
    }

    @PostMapping(value = "/enable")
    @Operation(summary = "启用流水线")
    @Parameters({
            @Parameter(required = false,name = "folderPath",description = "单级或多级目录"),
            @Parameter(required = true,name = "name",description = "流水线名称"),
    })
    public boolean enableJob(@RequestParam(required = false,defaultValue = "") String folderPath, String name){
        ResponseEntity<String> entity = jobsApi.enable(folderPath, name);
        boolean enableF = entity.getStatusCode().is3xxRedirection();
        if (enableF){
            log.info("[" + name +"]:enable成功");
        }else {
            log.error("[" + name +"]:enable失败");
        }
        return enableF;
    }

    @PostMapping(value = "/disable")
    @Operation(summary = "禁用流水线")
    @Parameters({
            @Parameter(required = false,name = "folderPath",description = "单级或多级目录"),
            @Parameter(required = true,name = "name",description = "流水线名称"),
    })
    public boolean disableJob(@RequestParam(required = false,defaultValue = "") String folderPath, String name){
        ResponseEntity<String> entity = jobsApi.disable(folderPath, name);
        boolean disableF = entity.getStatusCode().is3xxRedirection();
        if (disableF){
            log.info("[" + name +"]:disable成功");
        }else {
            log.error("[" + name +"]:disable失败");
        }
        return disableF;
    }

    @PostMapping(value = "/build")
    @Operation(summary = "流水线执行无参构建")
    @Parameters({
            @Parameter(required = false,name = "folderPath",description = "单级或多级目录"),
            @Parameter(required = true,name = "name",description = "流水线名称"),
    })
    public boolean buildJob(@RequestParam(required = false,defaultValue = "") String folderPath, String name){
        ResponseEntity<String> entity = jobsApi.build(folderPath, name);
        boolean xxSuccessful = entity.getStatusCode().is2xxSuccessful();
        if (xxSuccessful){
            log.info("[" + name +"]:build成功");
        }else {
            log.error("[" + name +"]:build失败");
        }
        return xxSuccessful;
    }

    @PostMapping(value = "/buildWithParameters")
    @Operation(summary = "流水线执行参数化构建")
    @Parameters({
            @Parameter(required = false,name = "folderPath",description = "单级或多级目录"),
            @Parameter(required = true,name = "name",description = "流水线名称"),
            @Parameter(required = false,name = "json",description = "构建参数,一个json字符串"),
    })
    public boolean buildJobWithParameters(@RequestParam(required = false,defaultValue = "") String folderPath,
                                          String name,
                                          @RequestParam("json") String json){
        ResponseEntity<String> entity = jobsApi.buildWithParameters(folderPath, name,json);
        boolean xxSuccessful = entity.getStatusCode().is2xxSuccessful();
        if (xxSuccessful){
            log.info("[" + name +"]:buildJobWithParameters成功");
        }else {
            log.error("[" + name +"]:buildJobWithParameters失败");
        }
        return xxSuccessful;
    }

    @PostMapping(value = "/stop")
    @Operation(summary = "取消构建")
    @Parameters({
            @Parameter(required = false,name = "folderPath",description = "单级或多级目录"),
            @Parameter(required = true,name = "name",description = "流水线名称"),
            @Parameter(required = true,name = "number",description = "构建编号"),
    })
    public boolean stopJob(@RequestParam(required = false,defaultValue = "") String folderPath, String name,String number){
        ResponseEntity<String> entity = jobsApi.stop(folderPath, name,number);
        boolean stopF = entity.getStatusCode().is3xxRedirection();
        if (stopF){
            log.info("[" + name + "#" + number +"]:stop成功");
        }else {
            log.error("[" + name + "#" + number +"]:stop失败");
        }
        return stopF;
    }

    @PostMapping(value = "/kill")
    @Operation(summary = "杀死构建")
    @Parameters({
            @Parameter(required = false,name = "folderPath",description = "单级或多级目录"),
            @Parameter(required = true,name = "name",description = "流水线名称"),
            @Parameter(required = true,name = "number",description = "构建编号"),
    })
    public boolean killJob(@RequestParam(required = false,defaultValue = "") String folderPath, String name,String number){
        ResponseEntity<String> entity = jobsApi.stop(folderPath, name,number);
        boolean killF = entity.getStatusCode().is3xxRedirection();
        if (killF){
            log.info("[" + name + "#" + number +"]:kill成功");
        }else {
            log.error("[" + name + "#" + number +"]:kill失败");
        }
        return killF;
    }

    @GetMapping(value = "/buildLog/{number}")
    @Operation(summary = "获取构建日志")
    @Parameters({
            @Parameter(required = false,name = "folderPath",description = "单级或多级目录"),
            @Parameter(required = true,name = "name",description = "流水线名称"),
            @Parameter(required = true,name = "number",description = "构建编号,可以是lastBuild、firstBuild等"),
    })
    public ProgressiveText getBuildLog(@RequestParam(required = false,defaultValue = "") String folderPath,
                                       String name,
                                       String start,
                                       @PathVariable String number){
        ResponseEntity<String> entity = jobsApi.progressiveText(folderPath, name, number,start);
        if (Objects.isNull(entity)) return null;
        ProgressiveText progressiveText = new ProgressiveText();
        progressiveText.setText(entity.getBody());
        progressiveText.setSize(Integer.parseInt(Objects.requireNonNull(entity.getHeaders().getFirst(HeadersConstants.X_TEST_SIZE))));
        progressiveText.setHasMoreDate(Boolean.parseBoolean(entity.getHeaders().getFirst(HeadersConstants.X_MORE_DATA)));
        return progressiveText;
    }

    @PostMapping(value = "/rename")
    @Operation(summary = "流水线重命名")
    @Parameters({
            @Parameter(required = false,name = "folderPath",description = "单级或多级目录"),
            @Parameter(required = true,name = "name",description = "流水线名称"),
            @Parameter(required = true,name = "newName",description = "新名称"),
    })
    public boolean renameJob(@RequestParam(required = false,defaultValue = "") String folderPath, String name,String newName){
        boolean rename = jobsApi.rename(folderPath, name, newName);
        if (rename){
            log.info("[" + name +"]:重命名成功");
        }else {
            log.error("[" + name +"]:重命名失败");
        }
        return rename;
    }

    @GetMapping(value = "/runHistory")
    @Operation(summary = "获取流水行历史运行信息")
    @Parameters({
            @Parameter(required = false,name = "folderPath",description = "单级或多级目录"),
            @Parameter(required = true,name = "name",description = "流水线名称"),
    })
    public List<Workflow> getRunHistory(@RequestParam(required = false,defaultValue = "") String folderPath, String name,String newName){
        List<Workflow> workflows = jobsApi.runHistory(folderPath, name);
        return workflows;
    }

    @GetMapping(value = "/workflow")
    @Operation(summary = "获取获取构建阶段信息")
    @Parameters({
            @Parameter(required = false,name = "folderPath",description = "单级或多级目录"),
            @Parameter(required = true,name = "name",description = "流水线名称"),
            @Parameter(required = true,name = "number",description = "构建编号"),
    })
    public Workflow getWorkflow(@RequestParam(required = false,defaultValue = "") String folderPath,
                                   String name,
                                   String number){
        Workflow workflow = jobsApi.workflow(folderPath, name, number);
        return workflow;
    }

    @GetMapping(value = "/pipelineNode")
    @Operation(summary = "获取运行阶段详情")
    @Parameters({
            @Parameter(required = false,name = "folderPath",description = "单级或多级目录"),
            @Parameter(required = true,name = "name",description = "流水线名称"),
            @Parameter(required = true,name = "number",description = "构建编号"),
            @Parameter(required = true,name = "nodeId",description = "构建阶段id")
    })
    public PipelineNode getPipelineNode(@RequestParam(required = false,defaultValue = "") String folderPath,
                             String name,
                             String number,
                             String nodeId){
        return jobsApi.pipelineNode(folderPath, name, number, nodeId);
    }


    /**
     * 对job列表（包含有job和文件夹）进行循环处理，得到只有job的集合
     * @param originJobs：jobsApi#jobList查询出的job列表
     * @return
     */
    private List<Job> exchangeJob(List<Job> originJobs){
        List<Job> destJobs = new ArrayList<>(100);
        for (int i = 0; i < originJobs.size(); i++){
            Job firstJob = originJobs.remove(0);
            String clazz = firstJob.getClazz();
            if (StringUtils.hasText(clazz) && clazz.contains("folder")){
                // 如果是文件夹，则继续查询
                JobList jobList = jobsApi.jobList(firstJob.getFullName(), 1, "jobs[name,fullName,url]");
                if (jobList == null || jobList.getJobs() == null || jobList.getJobs().size() <= 0) continue;
                originJobs.addAll(jobList.getJobs());
                continue;

            }
            destJobs.add(firstJob);
        }
        return destJobs;
    }

}
