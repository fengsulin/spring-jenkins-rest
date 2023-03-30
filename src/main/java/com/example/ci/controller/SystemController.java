package com.example.ci.controller;

import com.example.ci.api.StatisticsApi;
import com.example.ci.api.SystemApi;
import com.example.ci.constants.HeadersConstants;
import com.example.ci.domain.statistics.OverallLoad;
import com.example.ci.domain.system.SystemInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@Slf4j
@AllArgsConstructor
@Tag(name = "系统管理接口")
@RequestMapping("/system")
@Lazy
public class SystemController {
    private final SystemApi systemApi;
    private final StatisticsApi statisticsApi;

    @GetMapping("/info")
    @Operation(summary = "获取系统描述")
    public SystemInfo getSystemInfo(){
        ResponseEntity<String> entity = systemApi.systemInfo();
        if (Objects.isNull(entity)) return null;
        HttpHeaders headers = entity.getHeaders();
        if (headers.size() <= 0) return null;
        SystemInfo systemInfo = new SystemInfo();
        systemInfo.setInstanceIdentity(headers.getFirst(HeadersConstants.X_INSTANCE_IDENTITY))
        .setHudsonVersion(headers.getFirst(HeadersConstants.X_HUDSON))
        .setJenkinsSession(headers.getFirst(HeadersConstants.X_JENKINS_SESSION))
        .setJenkinsVersion(headers.getFirst(HeadersConstants.X_JENKINS))
        .setServer(headers.getFirst(HeadersConstants.SERVER))
        .setSshEndpoint(headers.getFirst(HeadersConstants.X_SSH_ENDPOINT));
        return systemInfo;
    }

    @PostMapping("/restart")
    @Operation(summary = "重启Jenkins Server")
    public boolean restartServer(){
        ResponseEntity<String> entity = systemApi.restart();
        if (entity.getStatusCode().is2xxSuccessful() || entity.getStatusCode().is3xxRedirection()){
            log.info(">>>>>>>>>>>> Jenkins Server restart successful <<<<<<<<<<<<<<<<<<<");
            return true;
        }
        log.error(">>>>>>>>>>>> Jenkins Server restart failed <<<<<<<<<<<<<<<<<<<");
        return false;
    }

    @PostMapping("/safeRestart")
    @Operation(summary = "安全重启Jenkins Server")
    public boolean safeRestartServer(){
        ResponseEntity<String> entity = systemApi.safeRestart();
        if (entity.getStatusCode().is2xxSuccessful() || entity.getStatusCode().is3xxRedirection()){
            log.info(">>>>>>>>>>>> Jenkins Server safeRestart successful <<<<<<<<<<<<<<<<<<<");
            return true;
        }
        log.error(">>>>>>>>>>>> Jenkins Server safeRestart failed <<<<<<<<<<<<<<<<<<<");
        return false;
    }

    @PostMapping("/quietDown")
    @Operation(summary = "停掉Jenkins Server")
    public boolean quietDownServer(){
        ResponseEntity<String> entity = systemApi.quiteDown();
        if (entity.getStatusCode().is2xxSuccessful() || entity.getStatusCode().is3xxRedirection()){
            log.info(">>>>>>>>>>>> Jenkins Server quietDown successful <<<<<<<<<<<<<<<<<<<");
            return true;
        }
        log.error(">>>>>>>>>>>> Jenkins Server quietDown failed <<<<<<<<<<<<<<<<<<<");
        return false;
    }

    @PostMapping("/cancelQuietDown")
    @Operation(summary = "取消停掉Jenkins Server")
    public boolean cancelQuietDownServer(){
        ResponseEntity<String> entity = systemApi.cancelQuiteDown();
        if (entity.getStatusCode().is2xxSuccessful() || entity.getStatusCode().is3xxRedirection()){
            log.info(">>>>>>>>>>>> Jenkins Server cancelQuiteDown successful <<<<<<<<<<<<<<<<<<<");
            return true;
        }
        log.error(">>>>>>>>>>>> Jenkins Server cancelQuiteDown failed <<<<<<<<<<<<<<<<<<<");
        return false;
    }

    @GetMapping("/overallLoad")
    @Operation(summary = "获取系统负载信息")
    public OverallLoad overallLoad(){
        return statisticsApi.overallLoad();
    }
}
