package com.example.ci.resolver;

import com.example.ci.annotion.BodyParser;
import org.springframework.core.MethodParameter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.ReactiveAdapter;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.invoker.HttpRequestValues;
import org.springframework.web.service.invoker.HttpServiceArgumentResolver;

/**
 * 请求体参数转换器
 * 针对插件安装时，将插件id转换为一个xml的请求体
 */
public class BodyParserArgumentResolver implements HttpServiceArgumentResolver {

    private final ReactiveAdapterRegistry reactiveAdapterRegistry;

    public BodyParserArgumentResolver(ReactiveAdapterRegistry reactiveAdapterRegistry) {
        Assert.notNull(reactiveAdapterRegistry, "ReactiveAdapterRegistry is required");
        this.reactiveAdapterRegistry = reactiveAdapterRegistry;
    }

    public boolean resolve(@Nullable Object argument, MethodParameter parameter, HttpRequestValues.Builder requestValues) {
        RequestBody annot = parameter.getParameterAnnotation(RequestBody.class);
        if (annot == null) {
            return false;
        } else {
            if (argument != null) {
                BodyParser annotation = parameter.getParameterAnnotation(BodyParser.class);
                if (annotation != null) {
                    String value = annotation.value();
                    if (StringUtils.hasText(value)) {
                        String newArgument = value.replaceAll("\\{pluginId}", (String) argument);
                        argument = newArgument;
                    }
                }
                ReactiveAdapter reactiveAdapter = this.reactiveAdapterRegistry.getAdapter(parameter.getParameterType());
                if (reactiveAdapter == null) {
                    requestValues.setBodyValue(argument);
                } else {
                    MethodParameter nestedParameter = parameter.nested();
                    String message = "Async type for @RequestBody should produce value(s)";
                    Assert.isTrue(!reactiveAdapter.isNoValue(), message);
                    Assert.isTrue(nestedParameter.getNestedParameterType() != Void.class, message);
                    requestValues.setBody(reactiveAdapter.toPublisher(argument), ParameterizedTypeReference.forType(nestedParameter.getNestedGenericParameterType()));
                    }
                }
                return true;
            }
        }
}
