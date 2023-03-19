package com.example.runner.remote;

import com.example.runner.domain.crumb.Crumb;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

/**
 * crumb 操作接口
 */
@HttpExchange("/crumbIssuer")
public interface CrumbIssuerApi extends BaseApi{

    @GetExchange("/api/xml?xpath=concat(//crumbRequestField,\":\",//crumb)")
    Crumb crumb();
}
