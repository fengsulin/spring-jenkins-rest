package com.example.ci.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "file.upload")
@Component
public class UploadProperties {
    private List<String> allowTypes;
}
