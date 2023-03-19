package com.example.runner.config;

import com.example.runner.auth.IAuth;
import com.example.runner.remote.*;
import com.example.runner.resolver.BodyParserArgumentResolver;
import com.example.runner.resolver.PathParserArgumentResolver;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.time.Duration;

/**
 * @author: FSL
 * @date: 2023/3/15
 * @description: TODO
 */
@Configuration
public class HttpInterfaceConfig {

    @Resource
    private IAuth iAuth;

    @Bean
    public WebClient webClient(){
        DefaultUriBuilderFactory uriBuilderFactory = new DefaultUriBuilderFactory(iAuth.getBaseUrl());
        uriBuilderFactory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);
        return WebClient.builder()
                .defaultHeader("source","http-interface")
                .uriBuilderFactory(uriBuilderFactory)   // 为了兼容路径参数为多级目录，设置设置编码格式为NONE
                .codecs(item -> item.defaultCodecs().maxInMemorySize((1 << 20) * 10))    // 设置请求缓存buffer大小
                .filter((request, next) -> {
                    ClientRequest filtered = ClientRequest.from(request)
                            .header(HttpHeaders.AUTHORIZATION,iAuth.getAuth())
                            .build();
                    return next.exchange(filtered);
                }).build();
    }

    @Bean
    public JobsApi jobApi(WebClient client){
        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(client))
                .blockTimeout(Duration.ofSeconds(10))   // 设置socket连接超时
                .customArgumentResolver(new PathParserArgumentResolver())    // 自定义请求路径参数解析
                .build();
        return factory.createClient(JobsApi.class);
    }

    @Bean
    public SystemApi systemApi(WebClient client){
        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(client))
                .blockTimeout(Duration.ofSeconds(10))
                .build();
        return factory.createClient(SystemApi.class);
    }

    @Bean
    public QueueApi queueApi(WebClient client){
        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(client))
                .blockTimeout(Duration.ofSeconds(10))
                .build();
        return factory.createClient(QueueApi.class);
    }

    @Bean
    public CrumbIssuerApi crumbIssuerApi(WebClient client){
        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(client))
                .blockTimeout(Duration.ofSeconds(10))
                .build();
        return factory.createClient(CrumbIssuerApi.class);
    }

    @Bean
    public PluginManagerApi pluginManagerApi(WebClient client){
        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(client))
                .blockTimeout(Duration.ofSeconds(10))
                .customArgumentResolver(new BodyParserArgumentResolver(new ReactiveAdapterRegistry()))
                .build();
        return factory.createClient(PluginManagerApi.class);
    }

    @Bean
    public UserApi userApi(WebClient client){
        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(client))
                .blockTimeout(Duration.ofSeconds(10))
                .build();
        return factory.createClient(UserApi.class);
    }

    @Bean
    public StatisticsApi statisticsApi(WebClient client){
        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(client))
                .blockTimeout(Duration.ofSeconds(10))
                .build();
        return factory.createClient(StatisticsApi.class);
    }

}
