package com.example.ci.api;

import com.example.ci.domain.user.ApiToken;
import com.example.ci.domain.user.User;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange("/user")
public interface UserApi extends BaseApi{

    @GetExchange("/{user}/api/json")
    User get(@PathVariable String user);

    @PostExchange(value = "/{user}/descriptorByName/jenkins.security.ApiTokenProperty/generateNewToken",contentType = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ApiToken generateNewToken(@PathVariable String user, @RequestParam("newTokenName") String tokenName);

    @PostExchange(value = "/{user}/descriptorByName/jenkins.security.ApiTokenProperty/revoke",contentType = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ResponseEntity<String> revoke(@PathVariable String user,@RequestParam String tokenUuid);
}
