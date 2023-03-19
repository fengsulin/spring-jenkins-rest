package com.example.runner.domain.plugins;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class Plugin {
    private boolean active;
    private String backupVersion;
    private boolean bundled;
    private boolean deleted;
    @JsonAlias("downgradable")
    private boolean downGradable;
    private boolean enabled;
    private boolean hasUpdate;
    private String loginName;
    private String supportsDynamicLoad;
    private boolean pinned;
    private String requiredCoreVersion;
    private String shorName;
    private String url;
    private String version;
}
