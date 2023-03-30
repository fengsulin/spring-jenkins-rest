package com.example.ci.domain.system;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 系统信息类
 */
@Data
@Accessors(chain = true)
public class SystemInfo {
    private String hudsonVersion;
    private String jenkinsVersion;
    private String jenkinsSession;
    private String instanceIdentity;
    private String sshEndpoint;
    private String server;
}
