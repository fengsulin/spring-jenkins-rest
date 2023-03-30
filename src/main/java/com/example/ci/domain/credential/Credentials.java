package com.example.ci.domain.credential;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.util.List;

/**
 * @author: FSL
 * @date: 2023/3/29
 * @description: 凭证集合类
 */
@Data
public class Credentials {
    private String displayName;
    private Boolean global;
    private String fullDisplayName;
    private String urlName;
    private String description;
    private List<Credential> credentials;
}
