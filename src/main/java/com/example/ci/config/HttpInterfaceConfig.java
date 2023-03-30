package com.example.ci.config;

import com.example.ci.auth.AuthenticationType;
import com.example.ci.auth.JenkinsAuthentication;
import com.example.ci.constants.HeadersConstants;
import com.example.ci.api.*;
import com.example.ci.resolver.BodyParserArgumentResolver;
import com.example.ci.resolver.PathParserArgumentResolver;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

/**
 * @author: FSL
 * @date: 2023/3/15
 * @description: TODO
 */
@Configuration
@Lazy
public class HttpInterfaceConfig {

    @Resource
    private JenkinsAuthentication auth;
    @Resource
    private JenkinsProperties jenkinsProperties;
    private static final String CRUMB_HEADER = "Jenkins-Crumb";
    @Bean("webClient")
    public WebClient webClient(){
        DefaultUriBuilderFactory uriBuilderFactory = new DefaultUriBuilderFactory(jenkinsProperties.getUrl());
        uriBuilderFactory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);
        return WebClient.builder()
                .defaultHeader("source","http-interface")
                .uriBuilderFactory(uriBuilderFactory)   // 为了兼容路径参数为多级目录，设置设置编码格式为NONE
                .codecs(item -> item.defaultCodecs().maxInMemorySize((1 << 20) * 10))    // 设置请求缓存buffer大小
                .filter((request, next) -> {
                    ClientRequest.Builder builder = null;
                    // 构建认证
                    final String authHeader = auth.authType().getAuthScheme() + " " + auth.authValue();
                    if (auth.authType() == AuthenticationType.UsernameApiToken || auth.authType() == AuthenticationType.UsernamePassword){
                        builder = ClientRequest.from(request)
                                .header(HttpHeaders.AUTHORIZATION, authHeader);

                    }
                    // 如果是用户密码和匿名认证，且是post请求，就加入crumb，TODO 可以增加判断用户是否需要crumb
                    if(request.method().name().equalsIgnoreCase("POST") &&
                            (auth.authType() == AuthenticationType.Anonymous || auth.authType() == AuthenticationType.UsernamePassword)){
                        ResponseEntity<String> entity = WebClient.create(jenkinsProperties.getUrl())
                                .get()
                                .uri("/crumbIssuer/api/xml?xpath=concat(//crumbRequestField,\":\",//crumb)")
                                .header(HttpHeaders.AUTHORIZATION, authHeader)
                                .retrieve()
                                .toEntity(String.class)
                                .block();
                        assert entity != null;
                        List<String> strings = entity.getHeaders().get(HttpHeaders.SET_COOKIE);
                        assert strings != null;
                        String jenkinsSession = strings.stream().filter(s -> s.startsWith(HeadersConstants.JENKINS_COOKIES_JSESSIONID)).findFirst().orElse("");
                        String first = jenkinsSession.split(";")[0];
                        builder.body(BodyInserters.fromFormData(CRUMB_HEADER,crumbValue(Objects.requireNonNull(entity.getBody()))))
                                .header(HttpHeaders.COOKIE,first);
                    }
                    assert builder != null;
                    return next.exchange(builder.build());
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

    @Bean
    public CredentialsApi credentialsApi(WebClient client){
        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(client))
                .blockTimeout(Duration.ofSeconds(10))   // 设置socket连接超时
                .customArgumentResolver(new PathParserArgumentResolver())    // 自定义请求路径参数解析
                .build();
        return factory.createClient(CredentialsApi.class);
    }

    private static String crumbValue(String crumb){
        String value = crumb.split(":")[1];
        return value;
    }
}
