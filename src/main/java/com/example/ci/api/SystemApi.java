package com.example.ci.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

/**
 * Jenkins 系统操作api接口
 */
@HttpExchange
public interface SystemApi extends BaseApi{

    @GetExchange("/")
    ResponseEntity<String> systemInfo();

    @PostExchange("/quietDown")
    ResponseEntity<String> quiteDown();

    @PostExchange("/cancelQuietDown")
    ResponseEntity<String> cancelQuiteDown();

    @PostExchange("/restart")
    ResponseEntity<String> restart();

    @PostExchange("/safeRestart")
    ResponseEntity<String> safeRestart();

}
