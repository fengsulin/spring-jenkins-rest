package com.example.ci.constants;

import com.alibaba.fastjson.JSONObject;
import com.example.ci.common.JsonUtils;
import com.example.ci.domain.credential.ApiToken;
import com.example.ci.domain.credential.SecretText;
import com.example.ci.domain.credential.UsernamePwd;
import lombok.Getter;

/**
 * @author: FSL
 * @date: 2023/3/30
 * @description: 凭证类型枚举
 */
public enum CredentialType {
    USERNAME_PWD(1,"用户名密码凭证"){
        @Override
        public String apply(String credential,int opType) {
            UsernamePwd usernamePwd= JsonUtils.str2Bean(credential,UsernamePwd.class);
            String format = String.format("{\"\":\"0\",\"credentials\":{\"scope\":\"GLOBAL\",\"username\":\"%s\",\"usernameSecret\":false,\"password\":\"%s\",\"$redact\":\"password\",\"id\":\"%s\",\"description\":\"%s\",\"stapler-class\":\"com.cloudbees.plugins.credentials.impl.UsernamePasswordCredentialsImpl\",\"$class\":\"com.cloudbees.plugins.credentials.impl.UsernamePasswordCredentialsImpl\"}}", usernamePwd.getUsername(), usernamePwd.getPassword(), usernamePwd.getId(), usernamePwd.getDescription());
            if (1 == opType) return format;
            JSONObject jsonObject = JSONObject.parseObject(format);
            String credentials = jsonObject.getString("credentials");
            return credentials;
        }
    },
    SECRET_TEXT(2,"秘钥文本凭证") {
        @Override
        public String apply(String credential,int opType) {
            SecretText secretText = JsonUtils.str2Bean(credential, SecretText.class);
            String format = String.format("{\"\":\"6\",\"credentials\":{\"scope\":\"GLOBAL\",\"secret\":\"%s\",\"$redact\":\"secret\",\"id\":\"%s\",\"description\":\"%s\",\"stapler-class\":\"org.jenkinsci.plugins.plaincredentials.impl.StringCredentialsImpl\",\"$class\":\"org.jenkinsci.plugins.plaincredentials.impl.StringCredentialsImpl\"}}",secretText.getSecret(),secretText.getId(),secretText.getDescription());
            if (1 == opType) return format;
            JSONObject jsonObject = JSONObject.parseObject(format);
            String credentials = jsonObject.getString("credentials");
            return credentials;
        }
    },
    API_TOKEN(3,"api token凭证") {
        @Override
        public String apply(String credential,int opType) {
            ApiToken apiToken = JsonUtils.str2Bean(credential, ApiToken.class);
            String format = String.format("{\"\": \"2\", \"credentials\": {\"scope\": \"GLOBAL\", \"apiToken\": \"%s\", \"$redact\": \"apiToken\", \"id\": \"%s\", \"description\": \"%s\", \"stapler-class\": \"com.dabsquared.gitlabjenkins.connection.GitLabApiTokenImpl\", \"$class\": \"com.dabsquared.gitlabjenkins.connection.GitLabApiTokenImpl\"}}",apiToken.getApiToken(),apiToken.getId(),apiToken.getDescription());
            if (1 == opType) return format;
            JSONObject jsonObject = JSONObject.parseObject(format);
            String credentials = jsonObject.getString("credentials");
            return credentials;
        }
    },
    CERTIFICATE(4,"证书凭证") {
        @Override
        public String apply(String credential,int opType) {
            return null;
        }
    }
    // TODO 其他凭证类型待定义
    ;
    @Getter
    private final int code;
    @Getter
    private final String typeName;

    CredentialType(int code, String typeName) {
        this.code = code;
        this.typeName = typeName;
    }

    // 抽象方法，勇于生成请求凭证接口的json参数
    public abstract String apply(String credential,int opType);

}
