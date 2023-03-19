package com.example.runner.remote;

import com.example.runner.annotion.PathParser;
import com.example.runner.domain.job.*;
import jakarta.annotation.Nullable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.io.InputStream;
import java.util.List;

/**
 * @author: FSL
 * @date: 2023/3/15
 * @description: Job操作 api 接口
 */
@HttpExchange()
public interface JobsApi extends BaseApi{

    /**
     * 获取某一个目录下的job列表
     * @param folderPath：前缀路径，可选(注意，这里的required=false不会生效，因为webclient的defaultUriVariables(Map)，Map集合需要一个线程安全，所以key和value都不能为null)
     * @param depth：查询深度，0或1
     * @return
     */
    @GetExchange("{folderPath}/api/json")
    JobList jobList(@PathVariable(required = false) @PathParser String folderPath,
                    @RequestParam(name = "depth",required = false,defaultValue = "0") Integer depth,
                    @RequestParam(name = "tree",required = false) String tree);

    /**
     * @description: 查询Job详情
     * @author: FSL
     * @date: 2023/3/16
     * @param optionalFolderPath：可选的目录
     * @param jobName：流水线名称
     * @param depth：查询的深度
     * @param tree：查询过滤条件
     * @return: com.example.runner.domain.job.JobInfo
     */
    @GetExchange("{optionalFolderPath}/job/{name}/api/json")
    JobInfo jobInfo(@PathVariable(required = false) @PathParser String optionalFolderPath,
                    @PathVariable("name") String jobName,
                    @RequestParam(value = "depth",required = false) Integer depth,
                    @RequestParam(value = "tree",required = false) String tree);

    /**
     * 获取某一构建详情
     * @param optionalFolderPath：前缀路径，可选
     * @param jobName：job名称
     * @param buildNumber：构建id
     * @return
     */
    @GetExchange("{optionalFolderPath}/job/{name}/{number}/api/json")
    BuildInfo buildInfo(@PathVariable @PathParser String optionalFolderPath,
                        @PathVariable("name") String jobName,
                        @PathVariable("number") String buildNumber);

    /**
     * 下载制品
     * @param optionalFolderPath：前缀路径，可选
     * @param jobName：job名称
     * @param buildNumber：构建id
     * @param relativeArtifactPath：制品相对路径
     * @return
     */
    @GetExchange("{optionalFolderPath}/job/{name}/{number}/artifact/{relativeArtifactPath}")
    InputStream artifact(@PathVariable @PathParser String optionalFolderPath,
                         @PathVariable("name") String jobName,
                         @PathVariable("number") Integer buildNumber,
                         @PathVariable String relativeArtifactPath);


    @PostExchange(value = "{optionalFolderPath}/createItem",contentType = MediaType.APPLICATION_XML_VALUE)
    ResponseEntity<String> create(@PathVariable @PathParser String optionalFolderPath,
                                  @RequestParam("name") String jobName,
                                  @RequestBody String configXML);

    @GetExchange("{optionalFolderPath}/job/{name}/config.xml")
    String config(@PathVariable @PathParser String optionalFolderPath,
                  @PathVariable("name") String jobName);

    @PostExchange(value = "{optionalFolderPath}/job/{name}/config.xml",contentType = MediaType.TEXT_XML_VALUE)
    ResponseEntity<String> updateXML(@PathVariable @PathParser String optionalFolderPath,
                   @PathVariable("name") String jobName,
                   @RequestBody String configXML);

    @GetExchange("{optionalFolderPath}/job/{name}/description")
    String description(@PathVariable @PathParser String optionalFolderPath,
                       @PathVariable("name") String jobName);

    @PostExchange("{optionalFolderPath}/job/{name}/doDelete")
    ResponseEntity<String> delete(@PathVariable @PathParser String optionalFolderPath,
                                  @PathVariable("name") String jobName);

    @PostExchange("{optionalFolderPath}/job/{name}/enable")
    ResponseEntity<String> enable(@PathVariable @PathParser String optionalFolderPath,
                   @PathVariable("name") String jobName);

    @PostExchange("{optionalFolderPath}/job/{name}/disable")
    ResponseEntity<String> disable(@PathVariable(required = false) @PathParser String optionalFolderPath,
                    @PathVariable("name") String jobName);

    @PostExchange("{optionalFolderPath}/job/{name}/build")
    ResponseEntity<String> build(@PathVariable @PathParser String optionalFolderPath,
                                 @PathVariable("name") String jobName);

    @PostExchange("{optionalFolderPath}/job/{name}/{number}/stop")
    ResponseEntity<String> stop(@PathVariable @PathParser String optionalFolderPath,
                                @PathVariable("name") String jobName,
                                @PathVariable("number") String buildNumber);

    @PostExchange("{optionalFolderPath}/job/{name}/{number}/term")
    ResponseEntity<String> term(@PathVariable @PathParser String optionalFolderPath,
                                @PathVariable("name") String jobName,
                                @PathVariable("number") String buildNumber);

    @PostExchange("{optionalFolderPath}/job/{name}/{number}/kill")
    ResponseEntity<String> kill(@PathVariable @PathParser String optionalFolderPath,
                                @PathVariable("name") String jobName,
                                @PathVariable("number") String buildNumber);

    @PostExchange(value = "{optionalFolderPath}/job/{name}/buildWithParameters",contentType = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ResponseEntity<String> buildWithParameters(@PathVariable @PathParser String optionalFolderPath,
                                               @PathVariable("name") String jobName,
                                               @RequestParam("json") String json);

    @GetExchange("{optionalFolderPath}/job/{name}/lastBuild/buildNumber")
    String lastBuildNumber(@PathVariable @PathParser String optionalFolderPath,
                            @PathVariable("name") String jobName);

    @GetExchange("{optionalFolderPath}/job/{name}/lastBuild/buildTimestamp")
    String lastBuildTimestamp(@PathVariable @PathParser String optionalFolderPath,
                              @PathVariable("name") String jobName);


    @GetExchange("{optionalFolderPath}/job/{name}/{number}/logText/progressiveText")
    ResponseEntity<String> progressiveText(@PathVariable @PathParser String optionalFolderPath,
                                    @PathVariable("name") String jobName,
                                    @PathVariable("number") String buildNumber,
                                    @RequestParam("start") String start);


    @PostExchange("{optionalFolderPath}/job/{name}/doRename")
    boolean rename(@PathVariable @PathParser String optionalFolderPath,
                   @PathVariable("name") String jobName,
                   @RequestParam("name") String newName);


    @GetExchange("{optionalFolderPath}/job/{name}/wfapi/runs")
    List<Workflow> runHistory(@PathVariable @PathParser String optionalFolderPath,
                              @PathVariable("name") String jobName);

    @GetExchange("{optionalFolderPath}/job/{name}/{number}/wfapi/describe")
    Workflow workflow(@PathVariable @PathParser String optionalFolderPath,
                      @PathVariable("name") String jobName,
                      @PathVariable("number") String buildNumber);

    @GetExchange("{optionalFolderPath}/job/{name}/{number}/execution/node/{nodeId}/wfapi/describe")
    PipelineNode pipelineNode(@PathVariable @PathParser String optionalFolderPath,
                              @PathVariable("name") String jobName,
                              @PathVariable("number") String buildNumber,
                              @PathVariable("nodeId") String nodeId);

    @GetExchange("{optionalFolderPath}/job/{name}/{number}/testReport/api/json")
    String testReport(@PathVariable @PathParser String optionalFolderPath,
                      @PathVariable("name") String jobName,
                      @PathVariable("number") String buildNumber);
}
