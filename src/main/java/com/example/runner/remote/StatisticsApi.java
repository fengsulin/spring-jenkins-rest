package com.example.runner.remote;

import com.example.runner.domain.statistics.OverallLoad;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface StatisticsApi extends BaseApi{

    @GetExchange("/overallLoad/api/json")
    OverallLoad overallLoad();
}
