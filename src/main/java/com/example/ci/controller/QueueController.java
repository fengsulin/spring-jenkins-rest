package com.example.ci.controller;

import com.example.ci.domain.queue.QueueItem;
import com.example.ci.domain.queue.QueueItems;
import com.example.ci.api.QueueApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/queue")
@Slf4j
@AllArgsConstructor
@Tag(name = "Job队列管理接口")
@Lazy
public class QueueController {
    private final QueueApi queueApi;

    @GetMapping("/list")
    @Operation(summary = "获取队列")
    public QueueItems listQueueItem(){
        return queueApi.queue();
    }

    @GetMapping("/item")
    @Operation(summary = "通过队列Id获取队列条目")
    @Parameter(name = "queueId",required = true,description = "队列id")
    public QueueItem getQueueItem(@RequestParam("queueId") String queueId){
        return queueApi.queueItem(queueId);
    }

    @PostMapping("/cancelItem")
    @Operation(summary = "取消队列")
    @Parameter(name = "queueId",required = true,description = "队列id")
    public boolean cancelQueueItem(@RequestParam("queueId") String id){
        ResponseEntity<String> entity = queueApi.cancel(id);
        if (entity.getStatusCode().is3xxRedirection() || entity.getStatusCode().is2xxSuccessful()){
            log.info("[" +id +"]:队列取消成功");
            return true;
        }
        log.info("[" +id +"]:队列取消失败");
        return false;
    }
}
