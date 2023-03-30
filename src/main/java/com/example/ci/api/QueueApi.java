package com.example.ci.api;

import com.example.ci.domain.queue.QueueItem;
import com.example.ci.domain.queue.QueueItems;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

/**
 * job队列接口
 */
@HttpExchange("/queue")
public interface QueueApi extends BaseApi{

    @GetExchange("/api/json?depth=1")
    QueueItems queue();

    @GetExchange("/item/{queueId}/api/json?depth=1")
    QueueItem queueItem(@PathVariable String queueId);

    @PostExchange(value = "/cancelItem",contentType = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ResponseEntity<String> cancel(@RequestParam(name = "id") String id);
}
