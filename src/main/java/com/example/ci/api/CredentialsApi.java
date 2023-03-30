package com.example.ci.api;

import com.example.ci.domain.credential.Credentials;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

/**
 * @author: FSL
 * @date: 2023/3/29
 * @description: 凭证接口
 */
@HttpExchange
public interface CredentialsApi {

    @GetExchange("/manage/credentials/store/system/domain/_/api/json?depth=1")
    Credentials credentialList();

    @PostExchange("/manage/credentials/store/system/domain/_/credential/{id}/doDelete")
    ResponseEntity<String> deleteCredential(@PathVariable String id);

    @PostExchange(value = "/manage/credentials/store/system/domain/_/createCredentials",contentType = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ResponseEntity<String> createCredential(@RequestParam String json);

    @PostExchange(value = "/manage/credentials/store/system/domain/_/credential/{id}/updateSubmit",contentType = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ResponseEntity<String> updateCredential(@RequestParam String json, @PathVariable String id);
}
